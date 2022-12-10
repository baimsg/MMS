package com.baimsg.depository;

import com.baimsg.bean.User;
import com.baimsg.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class UserDepository {

    public static final List<User> users;

    static {
        Object obj = Tools.read("users.bat");
        if (obj == null) {
            users = new ArrayList<>();
        } else {
            users = (List<User>) obj;
        }
    }

    public static void insertUser(User user) {
        /*
         * 直接终止命令窗口会执行到这需要做一下判断
         */
        boolean merchant = user.getMerchant();
        if (merchant && user.getAddress() != null || !merchant && user.getBalance() != null) {
            users.add(user);
            save();
        }
    }

    public static void updateUser(User user) {
        users.remove(user);
        users.add(user);
        save();
    }

    private static void save() {
        Tools.save(users, "users.bat");
    }

    public static Boolean hasUser(String account) {
        for (User value : users) {
            if (account.equals(value.getAccount())) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasMerchant(String storeName) {
        for (User value : merchants()) {
            if (value.getStoreName().equals(storeName)) {
                return true;
            }
        }
        return false;
    }

    public static List<User> merchants() {
        List<User> list = new ArrayList<>();
        for (User value : users) {
            if (value.getMerchant()) {
                list.add(value);
            }
        }
        return list;
    }

    public static User userByStoreName(String storeName) {
        for (User value : users) {
            if (storeName.equals(value.getStoreName())) {
                return value;
            }
        }
        return null;
    }

    public static User user(String account) {
        for (User value : users) {
            if (account.equals(value.getAccount())) {
                return value;
            }
        }
        return null;
    }
}
