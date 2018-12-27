package cn.cjf.springboot.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Order {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime payTime;
    // 省略 GET SET ...
}