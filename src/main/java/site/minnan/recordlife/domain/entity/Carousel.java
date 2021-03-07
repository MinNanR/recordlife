package site.minnan.recordlife.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.sql.Timestamp;

@TableName("record_carousel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carousel {

    @TableId(type = IdType.AUTO)
    private Integer id;

    private String url;

    private String sketch;

    @Setter
    private Integer isShow;

    private Timestamp createTime;

    private Integer createUserId;

    private String createUserName;

    public static final Integer SHOW = 1;

    public static final Integer HIDE =0;
}
