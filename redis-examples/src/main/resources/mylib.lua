-- 模块通过spring事件监听器启动时自动注册
local field = 'total'
local function rushBuy_0(keys, args)
    -- 预操作商品记录的键
    local hkey = keys[1]
    -- 获取hash记录中的数量字段
    if tonumber(redis.call('hget', hkey, 'total')) == 0 then
        return -1
    end
    -- 还有剩余则减一，并返回剩余数量
    -- hincrby指令返回的是执行后字段的`数字`而非字符串
    return redis.call('hincrby', hkey, field, -1)
end
redis.register_function('rushBuy_0', rushBuy_0)


-- 判断 expireSec 秒内，执行超过 count 次返回 false
-- increx，redis:8.8新指令，整合自增/上下限/过期时间等
-- 对应 Redis 指令: increx expires:agentids:6561 byint 1 ubound 2 ex 5
local function expireByIncrex(keys, args)
    -- 强制转换参数为数字
    local count = tonumber(args[1])
    local expireSec = tonumber(args[2])
    -- 返回数组，[当前值, 执行步长]
    local r = redis.call('increx', keys[1], 'byint', 1, 'ubound', count, 'ex', expireSec)
    -- 数组索引从1计算
    -- 执行步长为0，未执行
    if r[2] == 0 then
        return false
    end
    return true
end
-- 以数据库编号，修改后缀
redis.register_function('expireByIncrex_0', expireByIncrex)

-- redis:8.8 前，传统实现方式
-- 判断 expireSec 秒内，执行超过 count 次返回 false
-- local function expireAPICount_0(keys, args)
--     local hkey = keys[1]
--     local expireSec = tonumber(args[1])
--     local maxCount = tonumber(args[2])
--     -- 直接获取当前值，如果不存在则返回 nil，返回字符串
--     local current = redis.call('get', hkey)
--     -- 如果存在且已经达到最大限制，直接拒绝
--     if current and tonumber(current) >= maxCount then
--         return false
--     end
--     -- 执行自增，返回数字
--     local newVal = redis.call('incr', hkey)
--     -- 如果自增后值为 1，说明是新建的 Key
--     -- Key 存在，就一定带有过期时间，避免死锁
--     if newVal == 1 then
--         redis.call('expire', hkey, expireSec)
--     end
--     return true
-- end
-- redis.register_function('expireAPICount_0', expireAPICount_0)