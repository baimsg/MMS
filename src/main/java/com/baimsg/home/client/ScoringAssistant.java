package com.baimsg.home.client;

import com.baimsg.bean.Movie;
import com.baimsg.bean.Ticket;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.depository.TicketDepository;
import com.baimsg.utils.Tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoringAssistant {

    private User user;

    private ScoringAssistant() {
        user = null;
    }

    public static ScoringAssistant newBuilder() {
        return new ScoringAssistant();
    }

    public ScoringAssistant user(User user) {
        this.user = user;
        return this;
    }

    public void handle() {
        List<Ticket> ticketList = TicketDepository.tickets(user);
        if (ticketList == null || ticketList.isEmpty()) {
            System.out.println("您暂时没有观看过电影哦！");
            ClientHome.init(user);
        } else {
            for (Ticket ticket : ticketList) {
                Movie movie = ticket.getMovie();
                System.out.println("影片：" + movie.getName());
                System.out.println("演员；" + movie.getStarring());
                System.out.println("时长：" + movie.getDuration());
                System.out.println("评分：" + movie.getScore());
                System.out.println("票价：" + movie.getUnivalent());
                System.out.println("票数：" + movie.getTicket());
                System.out.println("上映时间：" + movie.getShowTime());
                System.out.println("<------------->");
            }
            inputName();
            finish();
        }
    }

    private void inputName() {
        System.out.println("请输入需要评分的电影名:");
        String msg = readText();
        if (msg == null) return;
        Ticket ticket = TicketDepository.ticket(user, msg);
        if (ticket == null) {
            System.out.println("您没有观看过【" + msg + "】,继续评分其他电影？（Y/N）");
        } else {
            Movie movie = MovieDepository.movie(ticket.getMerchant(), msg);
            if (movie == null || !movie.isListing()) {
                System.out.println("【" + msg + "】已下架,继续评分其他电影？（Y/N）");
            } else {
                inputScore(movie, ticket.getMerchant());
            }
        }
    }

    private void inputScore(Movie movie, User merchant) {
        Map<User, Double> scores = movie.getScores();
        if (scores == null) scores = new HashMap<>();
        if (scores.containsKey(user)) {
            System.out.println("【" + movie.getName() + "】您已打过分，继续评分其他电影？（Y/N）");
        } else {
            System.out.println("请输入评分:（1-10）");
            String msg = readText();
            if (msg.matches("[0-9]+(.?)[0-9]*")) {
                double fen = Double.parseDouble(msg);
                if (fen > 10 || fen < 1) {
                    System.out.println("评分范围（1-10）");
                    inputScore(movie, merchant);
                } else {
                    scores.put(user, fen);
                    movie.setScores(scores);
                    save(movie, merchant);
                }
            }
        }

    }

    private void save(Movie movie, User merchant) {
        MovieDepository.updateMovie(merchant, movie);
        System.out.println("已评分【" + movie.getName() + "】继续评分其他电影？（Y/N）");
    }

    private void finish() {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                ScoringMovie.init(user);
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
