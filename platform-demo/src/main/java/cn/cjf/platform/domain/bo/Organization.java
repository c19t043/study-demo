package cn.cjf.platform.domain.bo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
@NoArgsConstructor
public class Organization implements Serializable {
    //编号
    private Long id;
    //组织机构名称
    private String name;
    //父编号
    private Long parentId;
    //父编号列表，如1/2/
    private String parentIds;
    private Boolean available = Boolean.FALSE;
    public String makeSelfAsParentIds() {
        return getParentIds() + getId() + "/";
    }

}
