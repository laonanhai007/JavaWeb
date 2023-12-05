package com.example.mapper.impl;

import com.example.entity.Account;
import com.example.entity.AccountDetails;
import com.example.entity.vo.AccountPreviewVo;
import com.example.mapper.AccountMapper;
import com.example.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountMapperImpl implements AccountMapper {
    @Override
    public Account selectAccountByUsernameOrEmail(String text) {
        String sql = "select id, username, password, email from account where username = ? or email = ?";
        Account account = new Account();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, text);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                account.setId(resultSet.getInt(1));
                account.setUsername(resultSet.getString(2));
                account.setPassword(resultSet.getString(3));
                account.setEmail(resultSet.getString(4));
            }
            JDBCUtil.close(resultSet, preparedStatement, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return account;
    }


    @Override
    public boolean insertAccount(Account account) {
        String sql = "insert into account(username, password, email) VALUES (?,?,?)";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getPassword());
            preparedStatement.setString(3, account.getEmail());
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public boolean exist(Account account) {
        String sql = "select * from account where username = ? or email = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, account.getUsername());
            preparedStatement.setString(2, account.getEmail());
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean next = resultSet.next();
            JDBCUtil.close(resultSet, preparedStatement, connection);
            return next;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updatePasswordByEmail(String email, String password) {
        String sql = "update account set password = ? where  email = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public AccountDetails selectAccountDetailsByUid(Integer uid) {
        String sql = "select uid, nick_name, avatar, gender, qq, wx, blog, specialty, grade, phone,`desc`  from account_details where uid = ?";
        AccountDetails accountDetails = new AccountDetails();
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountDetails.setUid(resultSet.getInt(1));
                accountDetails.setNickName(resultSet.getString(2));
                accountDetails.setAvatar(resultSet.getString(3));
                accountDetails.setGender(resultSet.getInt(4));
                accountDetails.setQq(resultSet.getString(5));
                accountDetails.setWx(resultSet.getString(6));
                accountDetails.setBlog(resultSet.getString(7));
                accountDetails.setSpecialty(resultSet.getString(8));
                accountDetails.setGrade(resultSet.getString(9));
                accountDetails.setPhone(resultSet.getString(10));
                accountDetails.setDesc(resultSet.getString(11));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return accountDetails;
    }

    @Override
    public boolean updateAccountDetails(AccountDetails accountDetails) {
        String sql = "update account_details set nick_name = ?,gender  = ?,qq = ?,wx = ?,blog = ?, specialty  =?,grade =  ?,phone  = ?,`desc` = ? where uid  =?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, accountDetails.getNickName());
            preparedStatement.setInt(2, accountDetails.getGender());
            preparedStatement.setString(3, accountDetails.getQq());
            preparedStatement.setString(4, accountDetails.getWx());
            preparedStatement.setString(5, accountDetails.getBlog());
            preparedStatement.setString(6, accountDetails.getSpecialty());
            preparedStatement.setString(7, accountDetails.getGrade());
            preparedStatement.setString(8, accountDetails.getPhone());
            preparedStatement.setString(9, accountDetails.getDesc());
            preparedStatement.setInt(10, accountDetails.getUid());
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateEmailById(String email, Integer id) {
        String sql = "update account set email = ? where  id = ?";
        return updateAccountColum(email, id, sql);
    }

    @Override
    public boolean updatePasswordById(String newPassword, Integer id) {
        String sql = "update account set password = ? where  id = ?";
        return updateAccountColum(newPassword, id, sql);

    }

    @Override
    public boolean insertAccountPrivacy(Integer uid) {
        String sql = "insert into account_privacy(uid) VALUES (?)";
        return syncAccount(uid, sql);
    }

    @Override
    public AccountPreviewVo selectAccountPreviewVoById(Integer uid) {
        AccountPreviewVo accountPreviewVo = new AccountPreviewVo();
        String sql = "select id,nick_name,avatar from account_details where uid = ?";
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accountPreviewVo.setId(resultSet.getInt(1));
                accountPreviewVo.setNickName(resultSet.getString(2));
                accountPreviewVo.setAvatar(resultSet.getString(3));
            }
            JDBCUtil.close(resultSet, preparedStatement, connection);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return accountPreviewVo;
    }

    @Override
    public boolean insertAccountDetails(Integer uid) {
        String sql = "insert into account_details(uid) VALUES (?)";
        return syncAccount(uid, sql);

    }

    private boolean syncAccount(Integer uid, String sql) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, uid);
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean updateAccountColum(String column, Integer id, String sql) {
        try {
            Connection connection = JDBCUtil.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, column);
            preparedStatement.setInt(2, id);
            int i = preparedStatement.executeUpdate();
            JDBCUtil.close(preparedStatement, connection);
            return i > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
