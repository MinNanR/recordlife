package site.minnan.recordlife.domain.aggregate;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.minnan.recordlife.infrastructure.enumerate.Currency;

import java.math.BigDecimal;

/***
 * 账户
 * @author Minnan on 2021/2/20
 */
@TableName("record_account")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 货币
     */
    private Currency currency;

    /**
     * 余额
     */
    private BigDecimal balance;

    /**
     * 总收入
     */
    private BigDecimal income;

    /**
     * 总支出
     */
    private BigDecimal expend;

    /**
     * 追加支出
     *
     * @param amount
     */
    public void addExpend(BigDecimal amount) {
        balance = balance.subtract(amount);
        expend = expend.add(amount);
    }

    /**
     * 追加收入
     * @param amount
     */
    public void addIncome(BigDecimal amount){
        balance = income.subtract(amount);
        income = income.add(amount);
    }
}
