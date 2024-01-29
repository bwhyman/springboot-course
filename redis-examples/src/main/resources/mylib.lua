-- 模块通过spring事件监听器启动时自动注册
local field = 'total'
local function rushBuy(keys, args)
    -- 预操作商品记录的键
    local hkey = keys[1]
    -- 获取hash记录中的数量字段
    local quantity = redis.call('hget', hkey, field)
    -- 需要转换格式
    if tonumber(quantity) == 0 then
        return -1
    end
    -- 还有剩余则减一。返回抢购成功
    return redis.call('hincrby', hkey, field, -1)
end
--
redis.register_function(
        'rushBuy',
        rushBuy
)