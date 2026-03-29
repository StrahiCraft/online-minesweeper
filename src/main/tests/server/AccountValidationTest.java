package server;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.database.DatabaseManager;

import static org.junit.jupiter.api.Assertions.*;

class AccountValidationTest {

    @BeforeAll
    static void connectDatabase(){
        DatabaseManager.connect();
    }


    @Test
    void accountExistence() {
        String existingUsernameToTest = "admin";
        String fakeUsernameToTest = "administrator";

        assertTrue(AccountValidation.accountExists(existingUsernameToTest));
        assertFalse(AccountValidation.accountExists(fakeUsernameToTest));
    }

    @AfterAll
    static void disconnectDatabase(){
        DatabaseManager.disconnect();
    }
}