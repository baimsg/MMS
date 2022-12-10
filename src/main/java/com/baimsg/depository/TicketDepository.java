package com.baimsg.depository;

import com.baimsg.bean.Movie;
import com.baimsg.bean.Ticket;
import com.baimsg.bean.User;
import com.baimsg.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketDepository {
    public static Map<User, List<Ticket>> tickets;

    static {
        Object obj = Tools.read("tickets.bat");
        if (obj == null) {
            tickets = new HashMap<>();
        } else {
            tickets = (Map<User, List<Ticket>>) obj;
        }
    }

    public static void insertTicket(User user, Ticket ticket) {
        List<Ticket> movieList = tickets(user);
        movieList.add(ticket);
        tickets.put(user, movieList);
        save();
    }

    private static void save() {
        Tools.save(tickets, "tickets.bat");
    }

    public static Boolean hasMovie(User user, Movie movie) {
        return ticket(user, movie.getName()) != null;
    }

    public static Ticket ticket(User user, String name) {
        for (Ticket value : tickets(user)) {
            if (name.equals(value.getMovie().getName())) {
                return value;
            }
        }
        return null;
    }

    public static List<Ticket> tickets(User user) {
        for (Map.Entry<User, List<Ticket>> entry : tickets.entrySet()) {
            if (entry.getKey().getAccount().equals(user.getAccount())) {
                return tickets.get(user);
            }
        }
        return new ArrayList<>();
    }
}
