package com.baimsg.home.merchant;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.home.Home;
import com.baimsg.utils.Tools;

import java.util.List;
import java.util.Scanner;

public class MerchantHome {
    private static User user;

    public static void init(User newUser) {
        user = newUser;
        showMenu();
        operate();
    }

    private static void showMenu() {
        System.out.println("商店名:" + user.getStoreName());
        System.out.println("电话:" + user.getTelephone());
        System.out.println("地址:" + user.getAddress());
        System.out.println("余额:" + user.getBalance());
        String menu = "电影商家功能如下 ->\n01·电影列表\n02·上架电影\n03·下架电影\n04·修改电影\n05·退出\n请输入序号或关键词";
        System.out.println(menu);
    }

    private static void operate() {
        String msg = Tools.home();
        if (msg == null) return;
        switch (msg) {
            case "1":
            case "01":
            case "电影列表":
                showMoves();
                break;
            case "2":
            case "02":
            case "上架电影":
                ReleaseMovies.init(user);
            case "3":
            case "03":
            case "下架电影":
                RevokeMovies.init(user);
                break;
            case "4":
            case "04":
            case "修改电影":
                ReviseMovies.init(user);
                break;
            case "5":
            case "05":
            case "退出":
                Home.init();
                break;
        }
    }

    private static void showMoves() {
        List<Movie> movies = MovieDepository.movies(user);
        if (movies.isEmpty()) {
            System.out.println("暂时没有上架电影哦！");
        } else {
            movies.sort((o1, o2) -> (int) (o2.getScore() - o1.getScore()));
            for (Movie movie : movies) {
                if (movie.isListing()) {
                    System.out.println("影片：" + movie.getName());
                    System.out.println("演员；" + movie.getStarring());
                    System.out.println("时长：" + movie.getDuration());
                    System.out.println("评分：" + movie.getScore());
                    System.out.println("票价：" + movie.getUnivalent());
                    System.out.println("票数：" + movie.getTicket());
                    System.out.println("上映时间：" + movie.getShowTime());
                    System.out.println("<------------->");
                }
            }
        }
        back();
    }

    private static void back() {
        System.out.println("回车即可返回");
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNextLine()) {
            init(user);
        }
    }

}
