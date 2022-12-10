package com.baimsg.home.merchant;

import com.baimsg.bean.User;
import com.baimsg.type.MerchantType;

public class RevokeMovies {
    public static void init(User user) {
        MerchantAssistant.newBuilder().user(user).merchantType(MerchantType.Revoke).handle();
    }
}
