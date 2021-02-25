package site.minnan.recordlife.domain.vo.account;

import lombok.Data;
import site.minnan.recordlife.domain.aggregate.Account;

import java.math.BigDecimal;

/**
 * 总数据
 *
 * @author Minnan on 2021/2/22
 */
@Data
public class TotalVO {

    private BigDecimal totalBalance;

    private BigDecimal totalExpend;

    private BigDecimal totalIncome;

    public void add(Account account) {
        this.totalBalance = this.totalBalance.add(account.getBalance());
        this.totalExpend = this.totalExpend.add(account.getExpend());
        this.totalIncome = this.totalIncome.add(account.getIncome());
    }
}
