package cn.cjf.ok2.domain.rabbitmq;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RabbitMqBook implements java.io.Serializable {

    private static final long serialVersionUID = -2164058270260403154L;

    private String id;
    private String name;
}