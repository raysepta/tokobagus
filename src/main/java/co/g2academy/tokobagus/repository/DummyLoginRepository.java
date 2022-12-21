package co.g2academy.tokobagus.repository;

import co.g2academy.tokobagus.model.User;

public class DummyLoginRepository {
    private static User loggedInUser;
    public static User getLoggedInUser() {
        return loggedInUser;
    }
    public static void setLoggedInUser(User loggedInUser) {
        DummyLoginRepository.loggedInUser = loggedInUser;
    }
}
