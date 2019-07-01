package com.tedu.sp09.service;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp09.service.fb.ItemFeignServiceFB;
import com.tedu.web.util.JsonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/26 14:35
 */
@FeignClient(name = "item-service", fallback = ItemFeignServiceFB.class)
public interface ItemFeignService {

    /**
     * Feign利用我们熟悉的StringMVC注解，来反向生成访问路径
     *
     * 根据Mapping中指定的路径，在主机地址后面拼接路径
     * http://localhost:8001/{orderId}
     *
     * 根据@PathVariable配置，把参数数据拼接到路径当中，向拼接后的路径转发调用
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    JsonResult<List<Item>> getItems(@PathVariable String orderId);

    /**
     * 根据配置，拼接下面的路径，并向下面路径转发请求
     * 在协议体
     * http://localhost:8001/decreaseNumber
     * @param items
     * @return
     */
    @PostMapping("/decreaseNumber")
    JsonResult<Object> decreaseNumber(@RequestBody List<Item> items);
}
