package com.baimsg.home.client;

import com.baimsg.bean.User;

public class BuyTickets {
    public static void init(User user) {
        BuyAssistant.newBuilder().user(user).handle();
    }

}
