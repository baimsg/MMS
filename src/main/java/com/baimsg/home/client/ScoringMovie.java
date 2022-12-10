package com.baimsg.home.client;

import com.baimsg.bean.User;

public class ScoringMovie {
    public static void init(User user) {
        ScoringAssistant.newBuilder().user(user).handle();
    }
}
