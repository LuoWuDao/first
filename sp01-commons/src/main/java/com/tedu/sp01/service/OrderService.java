package com.tedu.sp01.service;

import com.tedu.sp01.pojo.Order;

/**
 * @Author: LLH
 * @Date: 2019/6/25 9:46
 */
public interface OrderService {
    Order getOrder(String orderId);
    void addOrder(Order order);
}
