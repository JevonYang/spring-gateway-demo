--
-- Created by IntelliJ IDEA.
-- User: jeveon
-- Date: 19-1-22
-- Time: 下午4:09
-- To change this template use File | Settings | File Templates.
--

local key = KEYS[1]
local init_max_permits = ARGV[1]
local init_rate = ARGV[2]
local permits = ARGV[3]
local curr_mill_second = ARGV[4]


local rate_limit_info = redis.pcall("HMGET", key, "last_mill_second", "curr_permits", "max_permits", "rate") --, "apps"

local last_mill_second = rate_limit_info[1]
local curr_permits = tonumber(rate_limit_info[2])
local max_permits = tonumber(rate_limit_info[3])
local rate = rate_limit_info[4]

local expire_time = math.floor((init_max_permits/init_rate)*2)

if curr_permits == nil or max_permits== nil or rate == nil then
    redis.pcall("HMSET", key, "max_permits", init_max_permits, "rate", init_rate, "curr_permits", init_max_permits)
    -- redis.pcall("EXPIRE", key, expire_time)
    last_mill_second = curr_mill_second
    curr_permits = init_max_permits
    max_permits = init_max_permits
    rate = init_rate
end

local local_curr_permits = max_permits;

if (type(last_mill_second) ~= 'boolean'  and last_mill_second ~= nil) then
    local reverse_permits = math.floor(((curr_mill_second - last_mill_second) / 1000) * rate)
    local expect_curr_permits = reverse_permits + curr_permits;
    local_curr_permits = math.min(expect_curr_permits, max_permits);

    if (reverse_permits > 0) then
        redis.pcall("HSET", key, "last_mill_second", curr_mill_second)
    end
else
    redis.pcall("HSET", key, "last_mill_second", curr_mill_second)
end


local result = -1
if (local_curr_permits - permits >= 0) then
    result = 1
    redis.pcall("HSET", key, "curr_permits", local_curr_permits - permits)
else
    redis.pcall("HSET", key, "curr_permits", local_curr_permits)
end

-- redis.pcall("EXPIRE", key, expire_time)

return result

