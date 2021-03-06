package model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;


public class User {
    private UUID id;
    private String userName;
    private String password;
    @NotNull
    private Player player;
    @NotNull
    private GameSession session;

    public User(String userName, String password, GameSession session) {
        this.session = session;
        this.id = UUID.randomUUID();
        this.userName = userName;
        this.password = password;
        this.player = new Player(this);
    }

    public User(String userName, String password) {
        this.session = null;
        this.id = UUID.randomUUID();
        this.userName = userName;
        this.password = password;
        this.player = new Player(this);
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String changeName(String newName) {
        String oldName = this.userName;
        this.userName = newName;
        return oldName;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setSession(GameSession session) {
        this.session = session;
    }

    @Override
    public boolean equals(@NotNull Object object) {
        if (object.getClass() != User.class) return false;
        User user = (User) object;
        //return (this.userName.equals(user.userName));
        return this.id == user.id;
    }

    @Override
    public int hashCode() {
        int k = 7;
        int sum = 0;
        for (int i = 0; i < this.userName.length(); i++ ) {
            sum =+ k*this.userName.charAt(i);
        }
        return sum;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + this.userName + '\'' +
                '}';
    }
}
