package com.baimsg.bean;

import com.baimsg.utils.Tools;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Movie implements Serializable {
    private static final long serialVersionUID = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public long getUnivalent() {
        return univalent;
    }

    public void setUnivalent(long univalent) {
        this.univalent = univalent;
    }

    public long getTicket() {
        return ticket;
    }

    public void setTicket(long ticket) {
        this.ticket = ticket;
    }

    public String getShowTime() {
        return showTime;
    }

    public void setShowTime(String showTime) {
        this.showTime = showTime;
    }

    public Map<User, Double> getScores() {
        return scores;
    }

    public void setScores(Map<User, Double> scores) {
        this.scores = scores;
    }

    public Double getScore() {
        if (scores == null || scores.isEmpty()) return 0.0;
        double sum = 0.0;
        if (scores.size() > 2) {
            List<Map.Entry<User, Double>> list = new ArrayList<>(scores.entrySet());
            list.sort((o1, o2) -> (int) (o1.getValue() - o2.getValue()));
            list.remove(list.size() - 1);
            list.remove(0);
            for (Map.Entry<User, Double> entry : list) {
                sum += entry.getValue();
            }
            return Tools.toScore(sum / (list.size() - 2));
        } else {
            for (Map.Entry<User, Double> entry : scores.entrySet()) {
                sum += entry.getValue();
            }
            return Tools.toScore(sum / scores.size());
        }
    }

    public boolean isListing() {
        return listing;
    }

    public void setListing(boolean listing) {
        this.listing = listing;
    }

    private String name;
    private String starring;
    private String duration;
    private String showTime;
    private long univalent;
    private long ticket;
    private Map<User, Double> scores;

    private Boolean listing;

    private static final Gson gson = new Gson();

    @Override
    public String toString() {
        return gson.toJson(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return name.equals(movie.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
