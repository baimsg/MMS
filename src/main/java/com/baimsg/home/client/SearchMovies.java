package com.baimsg.home.client;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.depository.UserDepository;
import com.baimsg.utils.Tools;

import java.util.List;

public class SearchMovies {

    private User user;

    private SearchMovies() {
        user = null;
    }

    public static SearchMovies newBuilder() {
        return new SearchMovies();
    }

    public SearchMovies user(User user) {
        this.user = user;
        return this;
    }
    public void handle() {
        List<User> merchants = UserDepository.merchants();
        if (merchants.isEmpty()) {
            System.out.println("暂时没有商店！");
        } else {
            System.out.println("请输入电影关键词:");
            String keyword = readText();
            if (keyword == null) return;
            for (User value : merchants) {
                Movie movie = MovieDepository.movie(value, keyword);
                if (movie != null && movie.isListing()) {
                    System.out.println("商店名:" + value.getStoreName());
                    System.out.println("电话:" + value.getTelephone());
                    System.out.println("地址:" + value.getAddress());
                    System.out.println("<-----电影------>");
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
        System.out.println("您要继续搜索吗？（Y/N）");
        finish();
    }

    private void finish() {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                Search.init(user);
                break;
            case "N":
            case "n":
            case "否":
                ClientHome.init(user);
                break;
            default:
                System.out.println("指令异常！请选择（Y/N）");
                finish();
        }
    }

    private String readText() {
        return Tools.clientHome(user);
    }

}
