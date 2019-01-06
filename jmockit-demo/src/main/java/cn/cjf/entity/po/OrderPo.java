package cn.cjf.entity.po;

import lombok.Data;

@Data
public class OrderPo {
    private Long id;
    private Long productId;
    private Long totalPrice;
}
