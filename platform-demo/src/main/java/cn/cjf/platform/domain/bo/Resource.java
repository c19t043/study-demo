package cn.cjf.platform.domain.bo;

import cn.cjf.platform.constant.ResourceType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class Resource implements Serializable {
    //编号
    private Long id;
    //资源名称
    private String name;
    //资源类型
    private ResourceType type = ResourceType.menu;
    //资源路径
    private String url;
    //权限字符串
    private String permission;
    //父编号
    private Long parentId;
    //父编号列表
    private String parentIds;
    private Boolean available = Boolean.FALSE;

    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

    public boolean isRootNode() {
        return parentId == 0;
    }
}
