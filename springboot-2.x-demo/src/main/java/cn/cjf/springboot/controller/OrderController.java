package cn.cjf.springboot.controller;

import cn.cjf.springboot.entity.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author Levin
 * @since 2018/8/2 0002
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public Order query() {
        Order order = new Order();
        order.setPayTime(LocalDateTime.now());
        return order;
    }
}