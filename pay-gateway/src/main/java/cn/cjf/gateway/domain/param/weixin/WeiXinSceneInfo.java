package cn.cjf.gateway.domain.param.weixin;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * 场景信息
 */
@Data
public class WeiXinSceneInfo {
    /**
     * <pre>
     *     门店唯一标识
     *     String(32)
     * </pre>
     *
     * 门店id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * <pre>
     *     门店名称
     *     String(64)
     * </pre>
     *
     * 门店名称
     */
    @JSONField(name = "name")
    private String name;
    /**
     * <pre>
     *     门店所在地行政区划码，详细见《最新县及县以上行政区划代码》
     *     String(6)
     * </pre>
     *
     * 门店行政区划码
     */
    @JSONField(name = "area_code")
    private String areaCode;
    /**
     * <pre>
     *     门店详细地址
     *     String(128)
     * </pre>
     *
     * 门店详细地址
     */
    @JSONField(name = "address")
    private String address;
}
