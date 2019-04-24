package cn.redsoft.magician.core.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * redis 工具类
 *
 * @author fengfan 20190403
 */
@Component
public final class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> defaultRedisTemplate;


    public boolean hasKey(String key) {
        try {
            return defaultRedisTemplate.hasKey(key);
        } catch (Exception e) {
            return false;
        }

    }


    /**
     *  ----------------------------------- Map -------------------------------------------
     */

    /**
     * HashGet
     */
    public Object hget(String key, String item) {
        return defaultRedisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     */
    public Map<String, Object> hmget(String key) {
        //return defaultRedisTemplate.opsForHash().entries(key);
        HashOperations<String, String, Object> hps = defaultRedisTemplate.opsForHash();
        return hps.entries(key);
    }

    /**
     * 向hash表中放入数据
     */
    public boolean hset(String key, String item, Object value) {
        try {
            defaultRedisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 刷新hash中一个item的值
     */
    public boolean hRefreshItem(String key, String item, Object value) {
        // TODO 先删除 再 设置
        return false;
    }

    /**
     * 删除hash表中的值
     */
    public void hdel(String key, Object... item) {
        defaultRedisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 获取map 里所有的 key 在少数据量下用这个
     */
    public Set<String> hkeys(String key) {
        Set<Object> keys = defaultRedisTemplate.opsForHash().keys(key);
        return keys.stream().map(String::valueOf).collect(Collectors.toSet());
        //return Optional.ofNullable(keys).orElse(new HashSet<>()).stream().map(String::valueOf).collect(Collectors.toSet()); // keys不会是null
    }

    /**
     * 获取map 里所有的 key 在大数据量下用
     * TODO 20190422 -> 还未测试啊
     */
    public Set<String> hscan(String key) {
        Set<String> result = new HashSet<>();
        Cursor<Map.Entry<Object, Object>> curosr = defaultRedisTemplate.opsForHash().scan(key, ScanOptions.NONE);
        while (curosr.hasNext()) {
            Map.Entry<Object, Object> entry = curosr.next();
            result.add(entry.getKey().toString());
        }
        return result;
    }


}
