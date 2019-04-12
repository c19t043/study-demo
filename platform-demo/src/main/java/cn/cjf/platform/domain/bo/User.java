package cn.cjf.platform.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
public class User implements Serializable {
    //编号
    private Long id;
    //所属公司
    private Long organizationId;
    //用户名
    private String username;
    //密码
    private String password;
    //加密密码的盐
    private String salt;
    //拥有的角色列表
    private List<Long> roleIds;
    private Boolean locked = Boolean.FALSE;

    public String getRoleIdsStr() {
        if(CollectionUtils.isEmpty(roleIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for(Long roleId : roleIds) {
            s.append(roleId);
            s.append(",");
        }
        return s.toString();
    }

    public String getCredentialsSalt() {
        return username + salt;
    }
}
