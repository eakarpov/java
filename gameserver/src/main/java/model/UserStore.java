package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserStore {

    private static UserStore instance;

    private static final Logger log = LogManager.getLogger(TokenStore.class);
    private List<? extends User> credentials;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
    }

    private UserStore(List<? extends User> credentials) {
        this.credentials = credentials;
    }

    private UserStore(){
        credentials = new ArrayList<>();
    }

    public static UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public List<? extends User> getUsers() {
        return this.credentials;
    }

    public String writeJSON()  throws JsonProcessingException {
        List<String> array = this.getUserNames();
        return mapper.writeValueAsString(array);
    }

    private List<String> getUserNames() {
        List<String> result = new ArrayList<>();
        for (User elem :
                this.credentials) {
            result.add(elem.getUserName());
        }
        return result;
    }

    public static List<String> readJson(String json) throws IOException {
        UserStore store = mapper.readValue(json, UserStore.class);
        return store.getUserNames();
    }

}
