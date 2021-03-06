package model.network;

public enum SocketActions {
    USER_ATTEMPT_SIGN_IN,
    USER_SIGNED_IN_SUCCESSFULLY,
    USER_SIGNED_IN_ERROR,
    USER_ATTEMPT_SIGN_UP,
    USER_SIGNED_UP_SUCCESSFULLY,
    USER_SIGNED_UP_ERROR,
    USER_SIGNED_IN_AS_GUEST,
    USER_SIGNED_IN_AS_GUEST_SUCCESSFULLY,
    USER_ADDED_FUNDS,
    USER_LOGOUT,
    USER_DISCONNECTED,
    USER_ADDED_BET,
    USER_WON_BET,
    ADD_USER_TO_ROULETTE_LOBBY,
    ADD_USER_TO_HORSE_RACE_LOBBY,
    WINNING_ROULETTE_NUMBER_BROADCAST,
    OPEN_ROULETTE_BET_BROADCAST,
    CLOSE_ROULETTE_BET_BROADCAST,
    START_HORSE_RACE_BROADCAST,
    CLOSE_HORSERACE_BET_BROADCAST,
    OPEN_HORSERACE_BET_BROADCAST,
    USER_EXIT_LOBBY,
    START_SPINNING,
    UPDATE_DATABASE
}
