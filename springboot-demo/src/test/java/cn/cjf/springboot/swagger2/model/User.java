package cn.cjf.springboot.swagger2.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@ApiModel
@Data
@AllArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 8655851615465363473L;

    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    /**
     * @ApiModelProperty：hidden=false将对象包含的属性隐藏
     */
    @ApiModelProperty(value = "密码",hidden = false)
    private String password;
}