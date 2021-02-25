package site.minnan.recordlife.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 流水记录
 * @author Minnan on 2021/2/20
 */
@TableName("record_trade")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trade {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 账户id
     */
    private Integer accountId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 一级分类id
     */
    private Integer firstTypeId;

    /**
     * 二级分类id
     */
    private Integer secondTypeId;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 方向，支出/收入
     */
    private TradeDirection direction;

    /**
     * 记录时间
     */
    private Timestamp time;

    /**
     * 备注
     */
    private String remarks;
}
