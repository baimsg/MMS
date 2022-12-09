package com.baimsg.home.merchant;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.utils.Constant;
import com.baimsg.utils.Tools;

import java.util.Scanner;

public class ReleaseMovies {
    private static User user;

    private static Movie movie;

    public static void init(User newUser) {
        user = newUser;
        movie = new Movie();
        inputName();
        inputStarring();
        inputDuration();
        inputShowTime();
        inputUnivalent();
        inputTicket();
        movie.setListing(true);
        MovieDepository.insertMovie(user, movie);
        MerchantHome.init(user);
    }

    private static void inputName() {
        System.out.println("请输入电影名字:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            Movie mov = MovieDepository.movie(user, msg);
            if (mov != null && !mov.isListing()) {
                mov.setListing(true);
                MovieDepository.updateMovie(user, mov);
                System.out.println("【" + msg + "】上架成功！");
                MerchantHome.init(user);
            } else {
                movie.setName(msg);
            }
        } else {
            System.out.println("内容不能为空");
            inputName();
        }
    }

    private static void inputStarring() {
        System.out.println("请输入主演:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setStarring(msg);
        } else {
            System.out.println("内容不能为空");
            inputStarring();
        }
    }

    private static void inputDuration() {
        System.out.println("请输入时长:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setDuration(msg);
        } else {
            System.out.println("内容不能为空");
            inputDuration();
        }
    }

    private static void inputShowTime() {
        System.out.println("请输入上架时间:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setShowTime(msg);
        } else {
            System.out.println("内容不能为空");
            inputShowTime();
        }
    }

    private static void inputUnivalent() {
        System.out.println("请输入票价:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches(Constant.number)) {
            movie.setUnivalent(Long.parseLong(msg));
        } else {
            System.out.println("内容不能为空");
            inputUnivalent();
        }
    }

    private static void inputTicket() {
        System.out.println("请输入票数:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches(Constant.number)) {
            movie.setTicket(Long.parseLong(msg));
        } else {
            System.out.println("内容不能为空");
            inputTicket();
        }
    }

    private static String readText() {
        return Tools.merchantHome(user);
    }


}
