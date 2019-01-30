package cn.cjf.ok2.domain.swagger;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@ApiModel
@Data
@NoArgsConstructor
public class SwaggerUser implements Serializable {
    public SwaggerUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SwaggerUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private static final long serialVersionUID = 8655851615465363473L;

    private Long id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("密码")
    private String password;

    // TODO  省略get set
}