package persistence;

import model.users.User;

public interface UserDAO {
    User getUser(String username);
}