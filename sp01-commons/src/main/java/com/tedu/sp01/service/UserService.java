package com.tedu.sp01.service;

import com.tedu.sp01.pojo.User;

/**
 * @Author: LLH
 * @Date: 2019/6/25 9:46
 */
public interface UserService {
    User getUser(Integer id);
    void addScore(Integer id, Integer score);
}
