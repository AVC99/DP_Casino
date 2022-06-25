package persistence;

import model.users.UserSession;

public interface AuthenticationDAO {
    Boolean authenticateCredentials(UserSession userSession);
    Boolean registerCredentials(UserSession userSession);
}
