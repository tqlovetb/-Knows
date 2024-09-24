package tq.tyd.knows.kafka.vo;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.kafka.common.protocol.types.Field;

import java.io.Serializable;

@Data
@Accessors(chain=true)
public class Message implements Serializable {
    private Integer id;
    private String content;
    private Long time;

}
