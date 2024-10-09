-- 模块通过spring事件监听器启动时自动注册
local field = 'total'
local function rushBuy(keys, args)
    -- 预操作商品记录的键
    local hkey = keys[1]
    -- 获取hash记录中的数量字段，按字符串比较
    if redis.call('hget', hkey, field) == '0' then
        return -1
    end
    -- 还有剩余则减一，并返回剩余数量
    return redis.call('hincrby', hkey, field, -1)
end
redis.register_function('rushBuy', rushBuy)

-- 判断expireSec秒内，执行超过count次返回flase
local function expireAPICount(keys, args)
    local hkey = keys[1]
    -- lua函数参数仅支持传入java string/number类型
    -- 但均按redis string类型处理
    local expireSec = args[1]
    local count = args[2]
    -- 键不存在，则为时效内的第一次。此处返回数字`0/1`
    if redis.call('exists', hkey) == 0 then
        -- 同时创建记录与过期时间
        redis.call('setex', hkey, expireSec, 1)
        return true
    end
    -- 按字符串比较
    if redis.call('get', hkey) >= count then
        return false
    end
    -- incr指令仅支持+1
    redis.call('incr', hkey)
    return true
end
redis.register_function('expireAPICount', expireAPICount)