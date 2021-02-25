package site.minnan.recordlife.application.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import site.minnan.recordlife.application.provider.TradeProviderService;
import site.minnan.recordlife.application.service.AccountService;
import site.minnan.recordlife.domain.aggregate.Account;
import site.minnan.recordlife.domain.entity.JwtUser;
import site.minnan.recordlife.domain.mapper.AccountMapper;
import site.minnan.recordlife.domain.vo.ListQueryVO;
import site.minnan.recordlife.domain.vo.account.*;
import site.minnan.recordlife.infrastructure.enumerate.Currency;
import site.minnan.recordlife.infrastructure.exception.EntityAlreadyExistException;
import site.minnan.recordlife.infrastructure.exception.UnmodifiableException;
import site.minnan.recordlife.userinterface.dto.DetailsQueryDTO;
import site.minnan.recordlife.userinterface.dto.ListQueryDTO;
import site.minnan.recordlife.userinterface.dto.account.AddAccountDTO;
import site.minnan.recordlife.userinterface.dto.account.DeleteAccountDTO;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private TradeProviderService tradeProviderService;


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
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id", "account_name", "expend", "income");
        queryWrapper.eq("user_id", jwtUser.getId());
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
        JwtUser user = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CheckAccountResult> checkList = tradeProviderService.getUsingAccount(user.getId());
        Collection<Integer> ids = dto.getIds();
        List<CheckAccountResult> checkResult = checkList.stream()
                .filter(e -> ids.contains(e.getAccountId()))
                .collect(Collectors.toList());
        if (CollectionUtil.isNotEmpty(checkResult)) {
            String message = checkResult.stream().map(CheckAccountResult::getAccountName).collect(Collectors.joining(
                    ", "));
            throw new UnmodifiableException(StrUtil.format("账户【{}】已使用", message));
        }
        accountMapper.deleteBatchIds(ids);
    }

    /**
     * 查询账户详情
     *
     * @param dto
     */
    @Override
    public AccountInfoVO getAccountInfo(DetailsQueryDTO dto) {
        Account account = accountMapper.selectById(dto.getId());
        return AccountInfoVO.assemble(account);
    }

    @Override
    public TotalVO getTotalData() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return accountMapper.getTotalData(jwtUser.getId());
    }

    /**
     * 获取账户下拉框
     *
     * @return
     */
    @Override
    public ListQueryVO<AccountBox> getAccountBox() {
        JwtUser jwtUser = (JwtUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", jwtUser.getId());
        List<Account> accounts = accountMapper.selectList(queryWrapper);
        List<AccountBox> list = accounts.stream().map(AccountBox::assemble).collect(Collectors.toList());
        return new ListQueryVO<>(list, null);
    }
}
