package com.baimsg.utils;

import com.baimsg.bean.User;
import com.baimsg.home.Home;
import com.baimsg.home.merchant.MerchantHome;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Tools {
    public static Boolean isPassword(String msg) {
        if (msg.length() < 6) {
            System.out.println("密码不足6位");
            return false;
        }
        if (msg.length() > 16) {
            System.out.println("密码超过16位");
            return false;
        }
        if (hasChinese(msg)) {
            System.out.println("密码不能包含汉字");
            return false;
        }
        return true;
    }

    /**
     * 满足账号要求
     *
     * @param msg 内容
     * @return 满足
     */
    public static Boolean isAccount(String msg) {
        if (msg.length() < 6) {
            System.out.println("账号不足6位");
            return false;
        }
        if (msg.length() > 20) {
            System.out.println("账号超过20位");
            return false;
        }
        if (!msg.contains("@")) {
            System.out.println("账号必须包含@");
            return false;
        }
        if (!hasAlphabet(msg)) {
            System.out.println("账号必须包含字母");
            return false;
        }
        if (!hasNumber(msg)) {
            System.out.println("账号必须包含数字");
            return false;
        }
        if (!msg.matches(Constant.account)) {
            System.out.println("账号不合法");
            return false;
        }
        return true;
    }

    public static Boolean hasChinese(String msg) {
        return has(Constant.chinese, msg);
    }

    public static Boolean hasAlphabet(String msg) {
        return has(Constant.alphabet, msg);
    }

    public static Boolean hasNumber(String msg) {
        return has(Constant.number, msg);
    }

    public static Boolean has(String regex, String msg) {
        Pattern pattern = Pattern.compile(regex);
        for (char c : msg.toCharArray()) {
            if (pattern.matcher(String.valueOf(c)).find()) {
                return true;
            }
        }
        return false;
    }

    public static void save(Object obj, String path) {
        File file = new File(Constant.resource + path);
        file.getParentFile().exists();
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(file.getPath())))) {
            oos.writeObject(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object read(String path) {
        File file = new File(Constant.resource + path);
        if (!file.isFile()) return null;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(file.getPath())))) {
            return ois.readObject();
        } catch (Exception e) {
            System.out.println("数据库暂时没有数据");
        }
        return null;
    }

    /**
     * 统一处理返回首页逻辑
     * @return  返回null表示已返回首页
     */
    public static String home() {
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        if (msg.equals("exit()")) {
            Home.init();
            return null;
        } else {
            return msg;
        }
    }

    /**
     * 统一处理返回商家首页逻辑
     *
     * @param user 当前用户
     * @return 返回null表示已返回首页
     */
    public static String merchantHome(User user) {
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.nextLine();
        if (msg.equals("exit()")) {
            MerchantHome.init(user);
            return null;
        } else {
            return msg;
        }
    }
}
