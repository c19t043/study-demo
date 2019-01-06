package cn.cjf.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderBo {
    private Long id;
    private Long productId;
    private Long totalPrice;
}
