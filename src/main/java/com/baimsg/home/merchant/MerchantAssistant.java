package com.baimsg.home.merchant;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.depository.MovieDepository;
import com.baimsg.type.MerchantType;
import com.baimsg.utils.Constant;
import com.baimsg.utils.Tools;

public class MerchantAssistant {

    private Movie movie;
    private User user;

    private MerchantType merchantType;

    private MerchantAssistant() {
        movie = new Movie();
        user = null;
        merchantType = MerchantType.Release;
    }

    public static MerchantAssistant newBuilder() {
        return new MerchantAssistant();
    }

    /**
     * 设置当前登录账号
     *
     * @param user 用户
     * @return this
     */
    public MerchantAssistant user(User user) {
        this.user = user;
        return this;
    }

    /**
     * 设置操作类型（默认发布）
     *
     * @param merchantType 操作
     * @return this
     */
    public MerchantAssistant merchantType(MerchantType merchantType) {
        this.merchantType = merchantType;
        return this;
    }

    /**
     * 开始处理
     */
    public void handle() {
        System.out.println("请输入需要" + merchantType.getMsg() + "电影名字:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            Movie mov = MovieDepository.movie(user, msg);
            switch (merchantType) {
                case Release:
                    if (mov != null && !mov.isListing()) {
                        mov.setListing(true);
                        MovieDepository.updateMovie(user, mov);
                        System.out.println("【" + msg + "】" + merchantType.getMsg() + "成功！" + "继续" + merchantType.getMsg() + "其他影片？（Y/N）");
                        finish();
                    } else {
                        movie.setName(msg);
                        inputStarring();
                    }
                    break;
                case Revoke:
                    if (mov == null) {
                        System.out.println("【" + msg + "】不存在，继续" + merchantType.getMsg() + "其他影片？（Y/N）");
                    } else {
                        if (mov.isListing()) {
                            mov.setListing(false);
                            MovieDepository.updateMovie(user, mov);
                            System.out.println("已下架【" + msg + "】，继续" + merchantType.getMsg() + "其他影片？（Y/N）");
                        } else {
                            System.out.println("请勿重复下架【" + msg + "】，继续" + merchantType.getMsg() + "其他影片？（Y/N）");
                        }
                    }
                    finish();
                    break;
                case Revise:
                    if (mov == null) {
                        System.out.println("【" + msg + "】不存在，继续" + merchantType.getMsg() + "其他影片？（Y/N）");
                    } else {
                        movie = mov;
                        movie.setName(msg);
                        inputStarring();
                    }
                    finish();
                    break;
            }
        } else {
            System.out.println("内容不能为空");
            handle();
        }
    }

    private void inputStarring() {
        System.out.println("请输入主演:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setStarring(msg);
            inputDuration();
        } else {
            System.out.println("内容不能为空");
            inputStarring();
        }
    }

    private void inputDuration() {
        System.out.println("请输入时长:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setDuration(msg);
            inputShowTime();
        } else {
            System.out.println("内容不能为空");
            inputDuration();
        }
    }

    private void inputShowTime() {
        System.out.println("请输入上架时间:");
        String msg = readText();
        if (msg == null) return;
        if (!msg.isEmpty()) {
            movie.setShowTime(msg);
            inputUnivalent();
        } else {
            System.out.println("内容不能为空");
            inputShowTime();
        }
    }

    private void inputUnivalent() {
        System.out.println("请输入票价:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches(Constant.number)) {
            movie.setUnivalent(Long.parseLong(msg));
            inputTicket();
        } else {
            System.out.println("内容不能为空");
            inputUnivalent();
        }
    }

    private void inputTicket() {
        System.out.println("请输入票数:");
        String msg = readText();
        if (msg == null) return;
        if (msg.matches(Constant.number)) {
            movie.setTicket(Long.parseLong(msg));
            save();
        } else {
            System.out.println("内容不能为空");
            inputTicket();
        }
    }

    private void save() {
        switch (merchantType) {
            case Release:
                movie.setListing(true);
                MovieDepository.insertMovie(user, movie);
                break;
            case Revoke:
                break;
            case Revise:
                MovieDepository.updateMovie(user, movie);
                break;
        }
        System.out.println(merchantType.getMsg() + "成功,继续" + merchantType.getMsg() + "其他影片？（Y/N）");
        finish();
    }

    private void finish() {
        String msg = readText();
        switch (msg) {
            case "Y":
            case "y":
            case "是":
                switch (merchantType) {
                    case Release:
                        ReleaseMovies.init(user);
                        break;
                    case Revoke:
                        RevokeMovies.init(user);
                        break;
                    case Revise:
                        ReviseMovies.init(user);
                        break;
                }
                break;
            case "N":
            case "n":
            case "否":
                MerchantHome.init(user);
                break;
            default:
                System.out.println("指令异常！请选择（Y/N）");
                finish();
        }
    }

    private String readText() {
        return Tools.merchantHome(user);
    }

}
