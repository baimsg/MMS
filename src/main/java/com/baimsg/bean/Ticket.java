package com.baimsg.bean;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements Serializable {
    private static final long serialVersionUID = 0;

    private User merchant;
    private Movie movie;
    private long quantity;
    private static final Gson gson = new Gson();

    public User getMerchant() {
        return merchant;
    }

    public void setMerchant(User merchant) {
        this.merchant = merchant;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket that = (Ticket) o;
        return movie.equals(that.movie);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie);
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
