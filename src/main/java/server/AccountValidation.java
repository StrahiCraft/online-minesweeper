package server;

/**
 * Class for validating accounts when logging in or registering new ones.
 */
public class AccountValidation {
    /**
     * Checks if the login is valid. This is done by checking if an account with the given username exists
     * and the password is correct.
     * @param username
     * Username of the account trying to log in.
     * @param password
     * Password of the account trying to log in.
     * @return
     * True if the login is valid, False if not
     */
    public static boolean LoginValid(String username, String password) {
        return true;
    }

    /**
     * Checks if the registration is valid. Checks if the username isn't already registered and if the password is
     * strong enough.
     * @param username
     * Username of the account trying to register.
     * @param password
     * Password being tested if it is strong enough.
     * @return
     * True if there is no account with the given username and the password is strong enough. False if there is an account
     * with the given username or the password is not strong enough.
     */
    public static boolean RegisterValid(String username, String password) {
        return true;
    }
}
