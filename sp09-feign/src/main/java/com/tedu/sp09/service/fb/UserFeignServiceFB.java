package com.tedu.sp09.service.fb;

import com.tedu.sp01.pojo.User;
import com.tedu.sp09.service.UserFeignService;
import com.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @Author: LLH
 * @Date: 2019/6/26 16:00
 */
@Component
public class UserFeignServiceFB implements UserFeignService {

    @Override
    public JsonResult<User> getUser(Integer userId) {
        return JsonResult.err("无法获取用户信息");
    }

    @Override
    public JsonResult addScore(Integer userId, Integer score) {
        return JsonResult.err("无法增加用户积分");
    }

}
