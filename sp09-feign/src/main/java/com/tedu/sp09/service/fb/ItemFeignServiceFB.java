package com.tedu.sp09.service.fb;

import com.tedu.sp01.pojo.Item;
import com.tedu.sp09.service.ItemFeignService;
import com.tedu.web.util.JsonResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: LLH
 * @Date: 2019/6/26 15:59
 */
@Component
public class ItemFeignServiceFB implements ItemFeignService {

    @Override
    public JsonResult<List<Item>> getItems(String orderId) {
        return JsonResult.err("无法获取订单商品列表");
    }

    @Override
    public JsonResult decreaseNumber(List<Item> items) {
        return JsonResult.err("无法修改商品库存");
    }

}
