package cn.redsoft.magician.core.auth.utils;

import cn.redsoft.magician.core.common.utils.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 权限map 的缓存
 * @author fengfan 20190423
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class AuthRedisCacheUtil {

    private RedisUtil redisUtil;





}
