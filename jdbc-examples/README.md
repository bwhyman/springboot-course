# spring-data-jdbc-examples

#### Introductions
java17+springboot3.2

#### update 2023.06.13
DO主键类型String，数据表主键类型char(19)。  
MySQL8+驱动ResultSet geObject()方法，可自动将MySQL datetime类型映射为Java LocalDateTime类型。

#### @Id & @Table & @Column & @Version
@Id，修饰主键属性。更新/保存操作时判断  
@Table，修饰DO类，可指定映射数据表名称  
@Column，修饰属性，可指定映射数据段名称  
@Version，修饰映射乐观锁属性，更新时自动比较属性值  

#### @EnableJdbcAuditing & AuditorAware
支持自定义jdbc的主键生成策略，示例基于snowflake算法生成ID  
AuditorAware接口，同一类型仅支持一个实现  

#### @CreatedBy
@CreatedBy，声明属性自动生成，与AuditorAware配合使用。修饰在主键属性，指定通过自定义主键生成策略生成ID  
即示例，对修饰了@CreatedBy注解的String类型，调用snowflake算法生成的数据

#### @CreatedDate & @LastModifiedDate
@CreateDate，修饰LocalDateTime类，在创建对象时自动创建日期时间对象保存到数据库  
@LastModifiedDate，修饰LocalDateTime类，每次更新对象时自动创建日期时间对象，更新到数据库  

#### @ReadOnlyProperty
声明属性为数据库生成数据，持久化时会忽略，可用于修饰由数据库生成的日期时间属性。

#### CrudRepository
CrudRepository<T, ID>接口，提供了针对DO类的基本CRUD操作方法。T，操作的DO类型，ID，主键类型
- T save(S entity)方法，默认保存全部属性值，值为null时也会保存到数据库，因此会覆盖数据库设置的default值
- T save(S entity)方法，当保存对象id属性非空时，执行update更新方法，同样会覆盖全部属性值
- `Optional<T> findById(ID id)`
- `long count()`
- `Iterable<S> findAll()/findAllById(Iterable<ID> ids)`
- `Iterable<S> saveAll(Iterable<S> entities)`
- `void delete(T entity)/deleteById(ID id)/deleteAll()/deleteAllById(Iterable<ID> ids)`
- `boolean existsById(ID id)`


#### RowMapper & ResultSetExtractor
通过自定义映射类，可以实现将一次多表关联查询的结果集，部分结果映射为集合，部分映射为普通属性，封装在dto。  
RowMapper接口，自定义行的映射实现。传入的ResultSet是当前遍历的行，无需关闭rs资源。   
- T mapRow(ResultSet rs, int rowNum)，重写行映射方法。传入行结果集对象，手动完成映射封装。

ResultSetExtractor接口，自定义结果的映射实现。传入的ResultSet是整个结果集，无需关闭。例如将多条记录映射为一个对象组装一个集合
- T extractData(ResultSet rs)，重写映射规则方法。传入的ResultSet为整个结果集对象，与原生JDBC类似，需手动移动游标遍历结果集。

@Query注解通过rowMapperClass/resultSetExtractorClass指定自定义封装实现  

#### Updates & @Modifying
spring-data-jdbc的更新操作，同样基于save()方法。内部通过判断主键是否存在执行插入/更新操作  
save()方法会更新全部属性字段。即，对象的空值属性也会更新！！因此，更新局部属性数据时，必须先查询出全部，合并，再执行更新操作，与JPA相同。mybatis支持仅更新非null属性

如果预一次请求完成，可通过@Modifying+@Query+手写update语句实现，但不灵活

#### Concurrency
@Version，修饰乐观锁版本属性字段。  
悲观锁在SQL查询语句追加for update锁定。

#### PagingAndSortingRepository
支持分页/排序，大数据量基于上一页最后ID与limit手动分页更灵活  

#### QueryByExampleExecutor
QueryByExampleExecutor接口支持简单的基于字符串的动态查询语句，例如，开始/结束/首字母/忽略大小写等，不支持数值比较等复杂操作。 

#### VS mybatis-plus
优点：切换到r2dbc很方便；可自定义映射类型无需xml配置；也支持乐观锁版本控制属性；简单直观；  
缺点：没有代码逆向生成；缺少开箱即用的主键生成实现；不支持局部更新；不支持动态拼接查询语句；没有丰富的功能插件；   