package com.baimsg.register;

import com.baimsg.bean.User;
import com.baimsg.depository.UserDepository;
import com.baimsg.home.Home;
import com.baimsg.utils.Tools;

public class Register {
    private static User user = new User();

    private static int schedule = 0;

    public static void init(boolean isSup) {
        user = new User();
        schedule = 0;
        System.out.println("欢迎来到" + (isSup ? "商家" : "用户") + "注册功能！");
        user.setMerchant(isSup);
        if (isSup) {
            registerSupUser();
        } else {
            registerUser();
        }
        UserDepository.insertUser(user);
        Home.init();
    }

    private static void registerUser() {
        inputAccount();
        inputPassword();
        inputName();
        inputSex();
        inputTelephone();
        inputBalance();
    }

    private static void registerSupUser() {
        inputAccount();
        inputPassword();
        inputName();
        inputSex();
        inputTelephone();
        inputBalance();
        inputStoreName();
        inputAddress();
    }


    private static void inputAccount() {
        System.out.println("请输入账号名:");
        String msg = readText();
        if (msg == null) return;
        if (Tools.isAccount(msg)) {
            if (UserDepository.hasUser(msg)) {
                System.out.println("该账号已被占用");
                inputAccount();
            } else {
                user.setAccount(msg);
            }
        } else {
            inputAccount();
        }
    }

    private static void inputPassword() {
        System.out.println("请输入密码:");
        String msg = readText();
        if (msg == null) return;
        switch (schedule) {
            case 0:
                if (Tools.isPassword(msg)) {
                    schedule = 1;
                    user.setPassword(msg);
                    System.out.println("请再次确认您的密码！");
                    inputPassword();
                } else {
                    inputPassword();
                }
                break;
            case 1:
                if (!user.getPassword().equals(msg)) {
                    schedule = 0;
                    System.out.println("两次输入密码不一样！");
                    inputPassword();
                }
                break;
            default:
        }
    }

    private static void inputName() {
        System.out.println("请输您的名字:");
        String msg = readText();
        if (msg == null) return;
        if (msg.length() > 1) {
            user.setName(msg);
        } else {
            System.out.println("请有效名字");
            inputName();
        }
    }

    private static void inputSex() {
        System.out.println("您的性别？（男/女）");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches("[男女]")) {
            user.setSex(msg);
        } else {
            System.out.println("请选择有效性别");
            inputSex();
        }
    }

    private static void inputBalance() {
        System.out.println("请输入您的余额");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches("[0-9]{1,16}")) {
            user.setBalance(Long.valueOf(msg));
        } else {
            System.out.println("请选择有效金额");
            inputBalance();
        }
    }

    private static void inputTelephone() {
        System.out.println("请输手机号:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches("1[0-9]{10}")) {
            user.setTelephone(Long.valueOf(msg));
        } else {
            System.out.println("请输入有效手机号");
            inputTelephone();
        }
    }

    private static void inputAddress() {
        System.out.println("请输地址:");
        String msg = readText();
        if (msg == null) return;
        if (msg.length() > 3) {
            user.setAddress(msg);
        } else {
            System.out.println("请输入有效地址");
            inputAddress();
        }
    }

    private static void inputStoreName() {
        System.out.println("请输入商城名:");
        String msg = readText();
        if (msg == null) return;
        if (msg.length() > 1) {
            user.setStoreName(msg);
        } else {
            System.out.println("请输入有效商城名");
            inputStoreName();
        }

    }

    private static String readText() {
        return Tools.home();
    }

}
