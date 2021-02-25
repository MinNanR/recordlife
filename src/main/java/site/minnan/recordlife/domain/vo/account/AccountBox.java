package site.minnan.recordlife.domain.vo.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import site.minnan.recordlife.domain.aggregate.Account;


@Data
@AllArgsConstructor
public class AccountBox {

    private String value;

    private String text;

    public static AccountBox assemble(Account account) {
        return new AccountBox(account.getId().toString(), account.getAccountName());
    }
}
