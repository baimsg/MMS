package com.baimsg.home.client;

import com.baimsg.bean.Movie;
import com.baimsg.bean.Ticket;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.depository.TicketDepository;
import com.baimsg.depository.UserDepository;
import com.baimsg.utils.Constant;
import com.baimsg.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class BuyAssistant {
    private User user;

    private List<Movie> movies;

    private Ticket ticket;

    private BuyAssistant() {
        user = null;
        movies = new ArrayList<>();
        ticket = new Ticket();
    }

    public static BuyAssistant newBuilder() {
        return new BuyAssistant();
    }

    public BuyAssistant user(User user) {
        this.user = user;
        return this;
    }

    public void handle() {
        System.out.println("请输入商店名:");
        String msg = readText();
        if (msg == null) return;
        if (UserDepository.hasMerchant(msg)) {
            User value = UserDepository.userByStoreName(msg);
            if (value == null) return;
            System.out.println("商店名:" + value.getStoreName());
            System.out.println("电话:" + value.getTelephone());
            System.out.println("地址:" + value.getAddress());
            System.out.println("<-----电影------>");
            movies = MovieDepository.movies(value);
            if (movies.isEmpty()) {
                System.out.println("暂时没有上架电影哦！是否继续查看其他商店？（Y/N）");
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
                inputName(value);
            }
        } else {
            System.out.println("商店【" + msg + "】不存在，是否继续购买其他电影票？（Y/N）");
        }
        finish();
    }

    private void inputName(User merchant) {
        System.out.println("请输入电影名称:");
        String msg = readText();
        if (msg == null) return;
        Movie movie = MovieDepository.movie(merchant, msg);
        if (movie == null) {
            System.out.println(merchant.getStoreName() + "没有电影【" + msg + "】,继续购买其他票？（Y/N）");
            back(user);
        } else {
            ticket.setMovie(movie);
            inputQuantity(merchant);
        }
    }

    private void inputQuantity(User merchant) {
        System.out.println("请输入购买数量:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches(Constant.number)) {
            long num = Long.parseLong(msg);
            if (num > 0) {
                ticket.setQuantity(num);
                ticket.setMerchant(merchant);
                save(merchant);
            } else {
                System.out.println("购买数量必须大于1！");
                inputQuantity(merchant);
            }
        } else {
            System.out.println("请输入数字！");
            inputQuantity(merchant);
        }
    }

    private void save(User merchant) {
        long sum = ticket.getQuantity() * ticket.getMovie().getUnivalent();
        String name = ticket.getMovie().getName();
        long quantity = ticket.getQuantity();
        System.out.println("影片:" + name);
        System.out.println("数量:" + quantity);
        System.out.println("金额:" + sum);
        Movie movie = MovieDepository.movie(merchant, name);
        if (movie == null) return;
        if (user.getBalance() < sum) {
            System.out.println("购买失败,您的余额不足！继续购买其他影片（Y/N）");
        } else if (movie.getTicket() < quantity) {
            System.out.println("购买失败,库存只有" + movie.getTicket() + "张！继续购买其他影片（Y/N）");
        } else {
            /*
             * 更新库存
             */
            movie.setTicket(movie.getTicket() - quantity);
            MovieDepository.updateMovie(merchant, movie);
            /*
             * 更新商家余额
             */
            merchant.setBalance(merchant.getBalance() + sum);
            UserDepository.save();
            /*
             * 更新客户余额
             */
            user.setBalance(user.getBalance() - sum);
            UserDepository.save();
            /*
             *更新购票记录
             */
            TicketDepository.insertTicket(user, ticket);
            System.out.println("购买成功,继续购买其他影片？（Y/N）");
        }
        finish();
    }

    private void back(User user) {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                inputName(user);
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

    private void finish() {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                BuyTickets.init(user);
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
