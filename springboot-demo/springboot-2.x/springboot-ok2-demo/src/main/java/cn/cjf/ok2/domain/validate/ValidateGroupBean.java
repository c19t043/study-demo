package cn.cjf.ok2.domain.validate;

import cn.cjf.ok2.validate.ValidateGroupBeanTest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidateGroupBean {
    @NotEmpty(groups = {Default.class, ValidateGroupBeanTest.class}
            , message = "名称不能为空")
    private String name;
    @NotNull(groups = {ValidateGroupBeanTest.class},
            message = "年龄不能为空")
    private Integer age;
}
