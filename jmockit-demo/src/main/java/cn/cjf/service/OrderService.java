package cn.cjf.service;

import cn.cjf.entity.bo.OrderBo;

public interface OrderService {
    OrderBo findOrderById(Long id);
}
