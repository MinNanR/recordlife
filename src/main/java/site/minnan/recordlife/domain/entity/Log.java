package site.minnan.recordlife.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 日志
 * @author Minnan on 2021/2/25
 */
@TableName("record_log")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Log {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     *  账号
     */
    private String username;

    /**
     * IP
     */
    private String ip;

    /**
     * 操作时间
     */
    private Timestamp time;

    /**
     * 模块
     */
    private String module;

    /**
     * 操作
     */
    private String operation;

    /**
     * 操作内容
     */
    private String content;

}
