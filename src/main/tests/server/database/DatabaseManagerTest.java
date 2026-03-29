package server.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseManagerTest {

    @BeforeAll
    static void connectDatabase(){
        DatabaseManager.connect();
    }

    @Test
    void executeQuery() {
        ResultSet queryResult = DatabaseManager.executeQuery("SELECT password FROM player WHERE username = 'admin'");

        String expectedResult = "password";
        String gottenResult = "";

        try{
            if (queryResult.next()){
                gottenResult = queryResult.getString("password");
            }
        }
        catch (Exception e){
            System.out.println("Error with fetching account");
        }

        assertEquals(expectedResult, gottenResult);
    }

    @Test
    void executeUpdate() {
        boolean updateSuccessful = DatabaseManager.executeUpdate("UPDATE player SET password = '123' WHERE username = 'admin'");

        assertTrue(updateSuccessful);
    }

    @AfterAll
    static void disconnectDatabase(){
        DatabaseManager.disconnect();
    }
}