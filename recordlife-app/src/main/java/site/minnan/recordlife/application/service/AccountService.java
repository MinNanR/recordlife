package site.minnan.recordlife.application.service;

import site.minnan.recordlife.domain.vo.account.AccountInfoVO;
import site.minnan.recordlife.domain.vo.account.AccountVO;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;
import site.minnan.recordlife.userinterface.dto.account.AddAccountDTO;
import site.minnan.recordlife.userinterface.dto.account.DeleteAccountDTO;

/***
 *
 * @author Minnan on 2021/2/20
 */
public interface AccountService {

    /**
     * 添加账户
     *
     * @param dto
     */
    void addAccount(AddAccountDTO dto);

    /**
     * 查询账户列表
     *
     * @param dto
     * @return
     */
    ListQueryVO<AccountVO> getAccountList(ListQueryDTO dto);

    /**
     * 删除账户
     *
     * @param dto
     */
    void deleteAccount(DeleteAccountDTO dto);

    /**
     * 查询账户详情
     *
     * @param dto
     */
    AccountInfoVO getAccountInfo(DetailsQueryDTO dto);
}
