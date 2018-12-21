package cn.cjf.springboot.entity;

import cn.cjf.springboot.validate.Groups;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * **groups 属性的作用就让 @Validated 注解只验证与自身 value 属性相匹配的字段，可多个，
 * 只要满足就会去纳入验证范围；**我们都知道针对新增的数据我们并不需要验证 ID 是否存在，
 * 我们只在做修改操作的时候需要用到，因此这里将 ID 字段归纳到 Groups.Update.class 中去，
 * 而其它字段是不论新增还是修改都需要用到所以归纳到 Groups.Default.class 中
 *
 * @author Levin
 * @since 2018/6/7 0005
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookValidate2 {

    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    private Integer id;
    @NotBlank(message = "name 不允许为空", groups = Groups.Default.class)
    private String name;
    @NotNull(message = "price 不允许为空", groups = Groups.Default.class)
    private BigDecimal price;

    // 省略 GET SET ...
}