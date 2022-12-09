package com.baimsg.home.merchant;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.utils.Tools;

import java.util.Scanner;

public class RemoveMovies {

    private static User user;

    private static Movie movie;

    public static void init(User newUser) {
        user = newUser;
        System.out.println("请输入需要下架的影片名称");
        String msg = readText();
        if (msg == null) return;
        movie = MovieDepository.movie(user, msg);
        if (movie == null) {
            System.out.println("【" + msg + "】不存在，继续下架其他影片？（Y/N）");
        } else {
            if (movie.isListing()) {
                movie.setListing(false);
                MovieDepository.updateMovie(user, movie);
                System.out.println("已下架【" + msg + "】，继续下架其他影片？（Y/N）");
            } else {
                System.out.println("请勿重复下架【" + msg + "】，继续下架其他影片？（Y/N）");
            }
        }
        finish();
    }

    private static void finish() {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                init(user);
                break;
            case "N":
            case "n":
            case "否":
                MerchantHome.init(user);
                break;
            default:
        }
    }


    private static String readText() {
        return Tools.merchantHome(user);
    }


}
