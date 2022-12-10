package com.baimsg.home.client;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.depository.UserDepository;
import com.baimsg.home.Home;
import com.baimsg.utils.Tools;

import java.util.List;
import java.util.Scanner;

public class ClientHome {
    private static User user;

    public static void init(User newUser) {
        user = newUser;
        showMenu();
        operate();
    }

    private static void showMenu() {
        System.out.println("名字:" + user.getName());
        System.out.println("余额:" + user.getBalance());
        String menu = "电影客户功能如下 ->\n01·电影列表\n02·高分电影\n03·搜索电影\n04·购票功能\n05·评分功能\n06·退出\n请输入序号或关键词";
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
            case "高分电影":
                break;
            case "3":
            case "03":
            case "搜索电影":
                Search.init(user);
                break;
            case "4":
            case "04":
            case "购票功能":
                BuyTickets.init(user);
                break;
            case "5":
            case "05":
            case "评分功能":

                break;
            case "6":
            case "06":
            case "退出":
                Home.init();
                break;
        }
    }

    private static void showMoves() {
        List<User> merchants = UserDepository.merchants();
        if (merchants.isEmpty()) {
            System.out.println("暂时没有商店！");
        } else {
            for (User value : merchants) {
                System.out.println("商店名:" + value.getStoreName());
                System.out.println("电话:" + value.getTelephone());
                System.out.println("地址:" + value.getAddress());
                List<Movie> movies = MovieDepository.movies(value);
                System.out.println("<-----电影------>");
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
                System.out.println();
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
