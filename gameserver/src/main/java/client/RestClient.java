package client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import model.Token;
import model.User;
import model.UserStore;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class RestClient implements IRestClient {
    private static final Logger log = LogManager.getLogger(RestClient.class);
    private static final String PROTOCOL = "http";
    private static final String HOST = "localhost";
    private static final String PORT = "8080";
    private static final String SERVICE_URL = PROTOCOL + "://" + HOST + ":" + PORT;

    private static final OkHttpClient client = new OkHttpClient();

    /***
     * Registers user
     *
     * !!NOTE!! Registrations expire on server shutdown.
     *
     * @param user - username
     * @param password
     * @return true if successfully registered
     */
    @Override
    public boolean register(String user, String password) {
        MediaType mediaType = MediaType.parse("raw");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s&password=%s", user, password)
        );

        String requestUrl = SERVICE_URL + "/auth/register";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        return makeRequest(request);
    }

    /**
     *
     * @param user - username
     * @param password
     * @return token
     */
    @Override
    public Long login(String user, String password) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("login=%s&password=%s", user, password)
        );
        String requestUrl = SERVICE_URL + "/auth/login";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();

        try {
            Response response = client.newCall(request).execute();
            return Long.parseLong(response.body().string());
        } catch (NumberFormatException e) {
            log.warn("Login is not correct.", e);
            return null;
        } catch (IOException e) {
            log.warn("Something went wrong in login.", e);
            return null;
        }
    }

    /**
     *
     * @param token - token
     * @return boolean
     */
    @Override
    public boolean logout(Long token) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType, ""
        );
        String requestUrl = SERVICE_URL + "/auth/logout";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return makeRequest(request);
    }

    /**
     *
     * @param token - token
     * @param userName - token
     * @return boolean
     */
    @Override
    public boolean changeName(Long token, String userName) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,
                String.format("name=%s", userName)
        );
        String requestUrl = SERVICE_URL + "/auth/changeName";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        return makeRequest(request);
    }

    @Override
    public List getUsers(Long token) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(
                mediaType,""
        );
        String requestUrl = SERVICE_URL + "/auth/users";
        Request request = new Request.Builder()
                .url(requestUrl)
                .post(body)
                .addHeader("authorization","Bearer " + token)
                .addHeader("content-type", "application/x-www-form-urlencoded")
                .build();
        try {
            Response response = client.newCall(request).execute();
            String usersJson = response.body().string();
            return readJson(usersJson);
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return null;
        }
    }

    private boolean makeRequest(Request request) {
        try {
            Response response = client.newCall(request).execute();
            return response.isSuccessful();
        } catch (IOException e) {
            log.warn("Something went wrong in the request.", e);
            return false;
        }
    }

    private List readJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.readValue(json, List.class);
    }
}
