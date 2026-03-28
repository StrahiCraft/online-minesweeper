package utility.customTypes;

public enum ServerMessageType {
    QUIT,

    // REGISTRATION
    REGISTER,
    REGISTER_SUCCESS,
    REGISTER_FAIL,

    // LOGIN
    LOGIN,
    LOGIN_SUCCESS,
    LOGIN_FAIL,

    // LOBBY CREATION
    CREATE_LOBBY,
    CREATE_LOBBY_SUCCESS,
    CREATE_LOBBY_FAIL,

    // LOBBY MANAGEMENT
    DELETE_LOBBY,

}
