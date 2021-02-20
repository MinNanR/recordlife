package site.minnan.recordlife.application.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.service.AccountService;
import site.minnan.recordlife.domain.aggregate.Account;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.AccountMapper;
import site.minnan.recordlife.domain.vo.account.AccountVO;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.infrastructure.enumerate.Currency;
import site.minnan.recordlife.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;
import site.minnan.recordlife.userinterface.dto.account.AddAccountDTO;
import site.minnan.recordlife.userinterface.dto.account.DeleteAccountDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/***
 * 账户相关操作
 * @author Minnan on 2021/2/20
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;


    @Override
    public void addAccount(AddAccountDTO dto) {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer check = accountMapper.checkAccountNameUsed(dto.getAccountName(), jwtUser.getId());
        if (check != null) {
            throw new EntityAlreadyExistException("账户已存在");
        }
        Account newAccount = Account.builder()
                .accountName(dto.getAccountName())
                .balance(BigDecimal.ZERO)
                .expend(BigDecimal.ZERO)
                .income(BigDecimal.ZERO)
                .userId(jwtUser.getId())
                .currency(Currency.valueOf(dto.getCurrency().toUpperCase()))
                .build();
        accountMapper.insert(newAccount);
    }

    /**
     * 查询账户列表
     *
     * @param dto
     * @return
     */
    @Override
    public ListQueryVO<AccountVO> getAccountList(ListQueryDTO dto) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "account_name", "expend", "income");
        Page<Account> queryPage = new Page<>(dto.getPageIndex(), dto.getPageSize());
        IPage<Account> page = accountMapper.selectPage(queryPage, queryWrapper);
        List<AccountVO> list = page.getRecords().stream().map(AccountVO::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, page.getTotal());
    }

    /**
     * 删除账户
     *
     * @param dto
     */
    @Override
    public void deleteAccount(DeleteAccountDTO dto) {

    }
}
