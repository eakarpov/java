package client;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;


public class RestClientTest {
    private IRestClient client = new RestClient();
    private Long token;

    @Test
    public void register() throws Exception {
        String user = "test";
        String password = "test";
        assertTrue(client.register(user, password));
    }

    @Test
    public void loginRegistered() throws Exception {
        token = client.login("test", "test");
        assertNotNull(token);
        System.out.print(token);
    }

    @Test
    public void loginNotRegistered() throws Exception {
        token = client.login("tester", "test");
        assertNull(token);
        System.out.print(token);
    }

    @Test
    public void changeNameProper() throws Exception {
        token =  client.login("test", "test");
        boolean result = client.changeName(token, "tester");
        boolean result2 = client.changeName(token, "test");
        System.out.print(result);
        assertTrue(result2);
        assertTrue(result);
    }

    @Test
    public void changeNameNotProper() throws Exception {
        boolean result = client.changeName(1123123L, "test");
        assertFalse(result);
    }

    @Test
    public void getUsers() throws Exception {
        token =  client.login("test", "test");
        List result = client.getUsers(token);
        for(int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
        }
        assertNotNull(result);
    }

    @Test
    public void logoutProper() throws Exception {
        token =  client.login("test", "test");
        boolean result = client.logout(token);
        System.out.print(result);
    }

    @Test
    public void logoutNotProper() throws Exception {
        assertFalse(client.logout(123123123123L));
    }
}