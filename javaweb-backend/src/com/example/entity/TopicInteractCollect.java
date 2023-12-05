package entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TopicInteractCollect)表实体类
 *
 * @author makejava
 * @since 2023-12-04 20:52:13
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicInteractCollect {
    private Integer tid;
    
    private Integer uid;
    
    private Date createTime;

}

