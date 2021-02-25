package site.minnan.recordlife.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.recordlife.infrastructure.enumerate.TradeDirection;

/***
 * 记录分类
 * @author Minnan on 2021/2/20
 */
@TableName("dim_trade_type")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeType {


    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 一级分类id，一级分类此属性为0
     */
    private Integer parentId;

    /**
     * 一级分类名称
     */
    @TableField(exist = false)
    private String parentName;

    /**
     * 分类类型
     */
    private TradeDirection direction;
}
