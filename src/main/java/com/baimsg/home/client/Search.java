package com.baimsg.home.client;

import com.baimsg.bean.User;

public class Search {
    public static void init(User user) {
        SearchMovies.newBuilder().user(user).handle();
    }
}
