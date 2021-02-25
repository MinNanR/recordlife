package site.minnan.recordlife.domain.vo.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import site.minnan.recordlife.domain.aggregate.Account;

import java.math.BigDecimal;

/**
 * 账户详情
 */
@Data
@Builder
@AllArgsConstructor
public class AccountInfoVO {

    private String accountName;

    private String currency;

    private BigDecimal accountIncome;

    private BigDecimal accountExpend;

    public static AccountInfoVO assemble(Account account){
        return AccountInfoVO.builder()
                .accountName(account.getAccountName())
                .currency(account.getCurrency().getCurrencyName())
                .accountIncome(account.getIncome())
                .accountExpend(account.getExpend())
                .build();
    }
}
