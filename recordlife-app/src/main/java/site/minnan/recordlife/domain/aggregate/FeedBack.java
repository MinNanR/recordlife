package site.minnan.recordlife.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/***
 * 反馈
 * @author Minnan on 2021/2/20
 */
@TableName("record_feed_back")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedBack {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 反馈用户id
     */
    private Integer userId;

    /**
     * 标题
     */
    private String title;

    /**
     * 理由
     */
    private String reason;

    /**
     * 评分
     */
    private int score;

    /**
     * 照片
     */
    private String imgUrl;

    /**
     * 意见
     */
    private String opinion;

    /**
     * 反馈时间
     */
    private Timestamp time;
}
