package com.tedu.sp09.service.fb;

import com.tedu.sp01.pojo.Order;
import com.tedu.sp09.service.OrderFeignService;
import com.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

/**
 * @Author: LLH
 * @Date: 2019/6/26 16:01
 */
@Component
public class OrderFeignServiceFB implements OrderFeignService {

    @Override
    public JsonResult<Order> getOrder(String orderId) {
        return JsonResult.err("无法获取商品订单");
    }

    @Override
    public JsonResult addOrder() {
        return JsonResult.err("无法保存订单");
    }

}