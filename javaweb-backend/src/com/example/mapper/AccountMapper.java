package com.example.mapper;

import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.vo.AccountPreviewVo;

public interface AccountMapper {

    Account selectAccountByUsernameOrEmail(String text);

    boolean insertAccount(Account account);

    boolean insertAccountDetails(Integer uid);

    boolean exist(Account account);

    boolean updatePasswordByEmail(String email, String password);

    AccountDetails selectAccountDetailsByUid(Integer uid);

    boolean updateAccountDetails(AccountDetails accountDetails);

    boolean updateEmailById(String email, Integer id);

    boolean updatePasswordById(String newPassword, Integer id);

    boolean insertAccountPrivacy(Integer uid);

    AccountPreviewVo selectAccountPreviewVoById(Integer uid);
}
