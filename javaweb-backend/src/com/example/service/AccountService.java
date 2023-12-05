package com.example.service;

import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.vo.AccountPreviewVo;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

import java.security.GeneralSecurityException;

public interface AccountService {
    String login(String text, String password, HttpServletRequest request);

    String register(Account account,String code,HttpServletRequest request);

    String sendEmailCode(String email, int code,boolean needAccount) throws MessagingException, GeneralSecurityException;

    String startResetPassword(String email, String code, HttpServletRequest req);

    String endRestPassword(String email, String password);

    AccountDetails getAccountDetails(Account account);

    String saveAccountDetails(AccountDetails accountDetails);

    String updateEmail(String email,Integer id
    );

    String updatePassword(String oldPassword, String newPassword, Account account);

}
