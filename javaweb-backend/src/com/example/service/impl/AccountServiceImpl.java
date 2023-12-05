package com.example.service.impl;

import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.vo.AccountPreviewVo;
import com.example.mapper.AccountMapper;
import com.example.mapper.impl.AccountMapperImpl;
import com.example.service.AccountService;
import com.example.util.Const;
import com.example.util.Encrypt;
import com.example.util.SendEmail;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.platform.commons.util.StringUtils;

import java.security.GeneralSecurityException;

public class AccountServiceImpl implements AccountService {
    AccountMapper accountMapper = new AccountMapperImpl();

    @Override
    public String login(String text, String password, HttpServletRequest request) {
        if (StringUtils.isBlank(password)) {
            return "密码不能为空";
        }
        if (StringUtils.isBlank(text)) {
            return "请填写用户名";
        }
        Account account = accountMapper.selectAccountByUsernameOrEmail(text);
        if (account.getId() == null) {
            return "不存在此用户,请先注册";
        }
        if (!account.getPassword().equals(Encrypt.toMd5(password))) {
            return "密码错误";
        }
        request.getSession().setAttribute("account", account);
        return null;
    }

    @Override
    public String register(Account account, String code, HttpServletRequest request) {
        //TODO 参数校验
        String trueCode = (String) request.getSession().getAttribute(account.getEmail() + Const.EMAIL_CODE);
        if (trueCode == null) {
            return "注册前请先获取验证码";
        }
        if (!code.equals(trueCode)) {
            return "验证码错误";
        }
        if (accountMapper.exist(account)) {
            return "用户名或邮箱已存在";
        }
        account.setPassword(Encrypt.toMd5(account.getPassword()));
        if (!accountMapper.insertAccount(account)) {
            return "数据库异常";
        }
        Account selected = accountMapper.selectAccountByUsernameOrEmail(account.getUsername());
        if (!accountMapper.insertAccountDetails(selected.getId())) {
            return "用户详情数据库异常";
        }
        if (!accountMapper.insertAccountPrivacy(selected.getId())) {
            return "用户隐私数据库异常";
        }
        return null;
    }

    @Override
    public String sendEmailCode(String email, int code, boolean needAccount) throws MessagingException, GeneralSecurityException {
        if (!needAccount) {
            Account account = accountMapper.selectAccountByUsernameOrEmail(email);
            if (account.getId() != null) {
                return "邮件已存在请更换";
            }
        }
        SendEmail.send(email, String.valueOf(code), needAccount);
        return null;
    }

    @Override
    public String startResetPassword(String email, String code, HttpServletRequest req) {
        Account account = new Account();
        account.setEmail(email);
        if (!accountMapper.exist(account)) {
            return "请填写正确邮箱";
        }
        String trueCode = (String) req.getSession().getAttribute(email + Const.RESET_CODE);
        if (trueCode == null) {
            return "注册前请先获取验证码";
        }
        if (!code.equals(trueCode)) {
            return "验证码错误";
        }
        return null;
    }

    @Override
    public String endRestPassword(String email, String password) {
        // TODO 参数校验
        password = Encrypt.toMd5(password);
        if (accountMapper.updatePasswordByEmail(email, password)) {
            return null;
        } else {
            return "数据库插入异常";
        }
    }

    @Override
    public AccountDetails getAccountDetails(Account account) {
        return accountMapper.selectAccountDetailsByUid(account.getId());
    }

    @Override
    public String saveAccountDetails(AccountDetails accountDetails) {

        if (accountMapper.updateAccountDetails(accountDetails)) {
            return null;
        } else {
            return "数据库更新异常";
        }
    }

    @Override
    public String updateEmail(String email, Integer id) {
        Account account = new Account();
        account.setEmail(email);
        if (accountMapper.exist(account)) {
            return "已存在此邮箱";
        }
        if (accountMapper.updateEmailById(email, id)) {
            return null;
        } else {
            return "数据库异常";
        }
    }

    @Override
    public String updatePassword(String oldPassword, String newPassword, Account account) {
        oldPassword = Encrypt.toMd5(oldPassword);
        if (!oldPassword.equals(account.getPassword())) {
            return "原密码错误";
        }
        newPassword = Encrypt.toMd5(newPassword);
        if (accountMapper.updatePasswordById(newPassword, account.getId())) {
            return null;
        } else {
            return "数据库异常";
        }
    }



}
