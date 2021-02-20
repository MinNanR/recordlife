package site.minnan.recordlife.domain.vo.account;

import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.aggregate.Account;

import java.math.BigDecimal;

/***
 * 账户查询显示
 * @author Minnan on 2021/2/20
 */
@Data
@Builder
public class AccountVO {

    private Integer id;

    private String accountName;

    private BigDecimal expendAmount;

    private BigDecimal incomeAmount;

    public static AccountVO assemble(Account account){
        return AccountVO.builder()
                .id(account.getId())
                .accountName(account.getAccountName())
                .expendAmount(account.getExpend())
                .incomeAmount(account.getIncome())
                .build();
    }
}
