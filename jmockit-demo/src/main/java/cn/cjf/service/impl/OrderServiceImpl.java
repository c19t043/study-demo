package cn.cjf.service.impl;

import cn.cjf.entity.bo.OrderBo;
import cn.cjf.service.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{
    @Override
    public OrderBo findOrderById(Long id) {
        return new OrderBo(id,Long.valueOf("1"),Long.valueOf("1200"));
    }
}
