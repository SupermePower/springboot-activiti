package com.nb.user.controller;

import com.nb.user.model.Order;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author fly
 * @description
 * @date 2019/3/5 19:07
 **/
@RestController
@RequestMapping("/order")
public class OrderController {

    @PostMapping
    public int saveOrder() {
        System.out.println("保存订单");
        return 1;
    }

    @GetMapping
    public List<Order> queryOrder() {
        System.out.println("查询订单");
        return new LinkedList<>();
    }

    @PutMapping
    public int editOrder() {
        System.out.println("编辑订单");
        return 1;
    }

    @DeleteMapping("{orderId}")
    public int deleteOrder(@PathVariable Long orderId) {
        System.out.println("删除订单：" + orderId);
        return 1;
    }

    @GetMapping("{orderId}")
    public Order queryOrderDetail(@PathVariable Long orderId) {
        System.out.println("获取订单详情：" + orderId);
        return new Order();
    }
}