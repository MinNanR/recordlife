package site.minnan.recordlife.userinterface.dto.account;

import lombok.Data;

import java.util.Collection;

/**
 * 删除账户参数
 * @author Minnan on 2021/2/20
 */
@Data
public class DeleteAccountDTO {

    Collection<Integer> ids;
}
