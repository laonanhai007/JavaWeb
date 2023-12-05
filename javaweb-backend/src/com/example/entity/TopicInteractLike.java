package entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * (TopicInteractLike)表实体类
 *
 * @author makejava
 * @since 2023-12-04 20:52:24
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicInteractLike {
    private Integer tid;

    private Integer uid;

    private Date createTime;

}

