package server.auth;

import model.Token;
import model.TokenStore;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/auth")
public class Authentication {

    private static final Logger log = LogManager.getLogger(Authentication.class);
    private static ConcurrentHashMap<String, String> credentials;

   // public Authentication() {
   //     this.store = new TokenStore();
   //     this.credentials = new ConcurrentHashMap<>();
   // }
    static {
       credentials = new ConcurrentHashMap<>();
    }

    public static ConcurrentHashMap<String,String> getCredentials() {
        return credentials;
    }

    @POST
    @Path("register")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response register(@FormParam("login") String user,
                             @FormParam("password") String password) {

        if (user == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (credentials.putIfAbsent(user, password) != null) {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }

        log.info("New user '{}' registered", user);
        return Response.ok("User " + user + " registered.").build();
    }

    @POST
    @Path("login")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response authenticateUser(@FormParam("login") String userName,
                                     @FormParam("password") String password) {

        if (userName == null || password == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        try {
            // Authenticate the user using the credentials provided
            if (!authenticate(userName, password)) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
            // Issue a token for the user
            User user = new User(userName,password);
            Token token = TokenStore.getInstance().issueToken(user);
            log.info("User '{}' logged in", user);

            // Return the token on the response
            return Response.ok(Long.toString(token.getToken())).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private boolean authenticate(String user, String password) throws Exception {
        return password.equals(credentials.get(user));
    }


    @Authorized
    @POST
    @Path("logout")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response logoutUser(@HeaderParam("authorization") String strToken) {
        try {
            Token token = new Token(strToken.substring("Bearer".length()).trim());
            User user = TokenStore.getInstance().deleteToken(token);
            log.info("New user '{}' logged out", user);
            return Response.ok("User " + user + " logged out.").build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
//
    @Authorized
    @POST
    @Path("changeName")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")
    public Response changeUserName(@HeaderParam("authorization") String strToken, @FormParam("name") String newName) {
        try {
            Token token = new Token(strToken.substring("Bearer".length()).trim());
            String oldName = TokenStore.getInstance().changeName(token, newName);
            String password = credentials.get(oldName);
            credentials.remove(oldName);
            credentials.put(newName, password);
            User user = TokenStore.getInstance().getUserbyToken(token);
            log.info("User '{}'  changed name from {} to {}", user, oldName, newName);
            return Response.ok("User " + user + " changed name from " + oldName + " to " + newName + ".").build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    @Authorized
    @POST
    @Path("users")
    @Consumes("application/x-www-form-urlencoded")
    @Produces("application/json")
    public Response getUsers() {
        try {
            List users = getUserNames();
            log.info("UserStore got - {}", users);
            return Response.ok(writeJSON(users)).build();

        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private List getUserNames() {
        List result = new ArrayList<String>(credentials.size());
        for (Enumeration<String> middle = credentials.keys(); middle.hasMoreElements();) {
            result.add(middle.nextElement());
        }
        return result;
    }

    private String writeJSON(List array)  throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        return mapper.writeValueAsString(array);
    }
}
