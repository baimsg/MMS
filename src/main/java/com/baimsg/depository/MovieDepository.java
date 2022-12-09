package com.baimsg.depository;

import com.baimsg.bean.Movie;
import com.baimsg.bean.User;
import com.baimsg.utils.Tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDepository {

    public static Map<User, List<Movie>> movies;

    static {
        Object obj = Tools.read("movies.bat");
        if (obj == null) {
            movies = new HashMap<>();
        } else {
            movies = (Map<User, List<Movie>>) obj;
        }
    }

    public static void insertMovie(User user, Movie movie) {
        if (movie.getShowTime() != null) {
            List<Movie> movieList = movies(user);
            movieList.add(movie);
            movies.put(user, movieList);
            save();
            System.out.println("上架成功！");
        }
    }

    public static void updateMovie(User user, Movie movie) {
        List<Movie> movieList = movies(user);
        movieList.remove(movie);
        movieList.add(movie);
        movies.put(user, movieList);
        save();
    }

    private static void save() {
        Tools.save(movies, "movies.bat");
    }

    public static Boolean hasMovie(User user, Movie movie) {
        return movie(user, movie.getName()) != null;
    }

    public static Movie movie(User user, String name) {
        for (Movie value : movies(user)) {
            if (name.equals(value.getName())) {
                return value;
            }
        }
        return null;
    }

    public static List<Movie> movies(User user) {
        for (Map.Entry<User, List<Movie>> entry : movies.entrySet()) {
            if (entry.getKey().getAccount().equals(user.getAccount())) {
                return movies.get(user);
            }
        }
        return new ArrayList<>();
    }

}
