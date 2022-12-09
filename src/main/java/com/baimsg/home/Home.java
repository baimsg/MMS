package com.baimsg.home;

import com.baimsg.depository.UserDepository;
import com.baimsg.login.Login;
import com.baimsg.register.Register;

import java.util.Scanner;

public class Home {

    public static void init() {
        showMenu();
        operate();
    }

    private static void showMenu() {
        String menu = "首页功能如下 ->\n01·登录\n02·用户注册\n03·商家注册\n04·退出\n请输入序号或关键词";
        System.out.println(menu);
        for (int i = 0; i < UserDepository.users.size(); i++) {
            System.out.println(UserDepository.users.get(i).getAccount());
        }
    }

    /**
     * 用户选择操作
     */
    private static void operate() {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            String msg = scanner.next();
            switch (msg) {
                case "1":
                case "01":
                case "登录":
                    Login.init();
                    break;
                case "2":
                case "02":
                case "用户注册":
                    Register.init(false);
                    break;
                case "3":
                case "03":
                case "商家注册":
                    Register.init(true);
                    break;
                case "4":
                case "04":
                case "退出":
                    break;
                default:
                    System.out.println("not menu [" + msg + "]");
                    showMenu();
                    operate();
            }
        }
    }
}
