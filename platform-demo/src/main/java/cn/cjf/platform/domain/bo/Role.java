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
public class Role implements Serializable {
    //编号
    private Long id;
    //角色标识 程序中判断使用,如"admin"
    private String role;
    //角色描述,UI界面显示使用
    private String description;
    //拥有的资源
    private List<Long> resourceIds;
    //是否可用,如果不可用将不会添加给用户
    private Boolean available = Boolean.FALSE;

    public String getResourceIdsStr() {
        if(CollectionUtils.isEmpty(resourceIds)) {
            return "";
        }
        StringBuilder s = new StringBuilder();
        for(Long resourceId : resourceIds) {
            s.append(resourceId);
            s.append(",");
        }
        return s.toString();
    }
}
