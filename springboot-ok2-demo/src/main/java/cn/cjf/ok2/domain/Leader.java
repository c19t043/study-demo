package cn.cjf.ok2.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Leader {

    /**
     * 姓名
     */
    private String name;

    /**
     * 生日
     */
    @Pattern(regexp = "^[0-9]{4}-[0-9]{2}-[0-9]{2}$", message = "出生日期格式不正确")
    private String birthday;

    /**
     * 年龄
     */
    @Min(value = 0)
    private Integer age;

    //省略gettes and  setters

}