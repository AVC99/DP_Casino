package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Horse;
import model.users.GameResult;
import model.users.Guest;
import model.users.User;
import model.users.UserSession;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonManager {
    public static String toJSON(Serializable object) {
        return new Gson().toJson(object);
    }

    public static UserSession getUserSessionFromJson(String json) {
        return new Gson().fromJson(json, UserSession.class);
    }

    public static Guest getGuestFromJson(String json) {
        return new Gson().fromJson(json, Guest.class);
    }

    public static ArrayList<Horse> getHorsesFromJson(String json) {
        Type horseList= new TypeToken<ArrayList<Horse>>(){}.getType();
        return new Gson().fromJson(json, horseList);
    }
    public static Horse getHorseFromJson(String json) {
        return new Gson().fromJson(json, Horse.class);
    }
    public static GameResult getGameResultFromJson(String json) {
        return new Gson().fromJson(json, GameResult.class);
    }
    public static User getUserFromJson(String json) {
        return new Gson().fromJson(json, User.class);
    }
}
