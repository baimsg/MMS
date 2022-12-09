package com.baimsg.login;

import com.baimsg.bean.User;
import com.baimsg.depository.UserDepository;
import com.baimsg.home.client.ClientHome;
import com.baimsg.home.merchant.MerchantHome;
import com.baimsg.utils.Tools;

public class Login {

    private static String account;
    private static String password;

    public static void init() {
        account = null;
        password = null;
        inputAccount();
        inputPassword();
        login();
    }

    private static void login() {
        if (UserDepository.hasUser(account)) {
            User user = UserDepository.user(account);
            if (user == null) return;
            if (password.equals(user.getPassword())) {
                System.out.println("登录成功，欢迎回来" + user.getName());
                if (user.getMerchant()) {
                    MerchantHome.init(user);
                } else {
                    ClientHome.init(user);
                }
            } else {
                System.out.println("登录失败,密码错误！");
                init();
            }
        } else {
            System.out.println("登录失败,用户不存在！");
            init();
        }
    }

    private static void inputAccount() {
        System.out.println("请输入账号名:");
        String msg = readText();
        if (msg == null) return;
        account = msg;
    }

    private static void inputPassword() {
        System.out.println("请输入密码:");
        String msg = readText();
        if (msg == null) return;
        password = msg;
    }


    private static String readText() {
        return Tools.home();
    }


}
