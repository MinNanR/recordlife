package site.minnan.recordlife.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@TableName("record_carousel")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Carousel {
    private Integer id;

    private String url;

    private String sketch;

    private Integer isShow;

    private Timestamp createTime;

    private Integer createUserId;

    private String createUserName;

    public static final Integer SHOW = 1;

    public static final Integer HIDE =0;
}
