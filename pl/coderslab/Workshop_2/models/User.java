package pl.coderslab.Workshop_2.models;

import org.mindrot.jbcrypt.BCrypt;


public class User {
    private int id;
    private String userName;
    private String email;
    private String password;
    private int userGroupId;

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.hashPassword(password);
    }

    public int getUserGroupId() {
        return userGroupId;
    }

    public void setUserGroupId(Group group) {
        this.userGroupId = group.getId();
    }

    public void setUserGroupId(int userGroupId) {
        this.userGroupId = userGroupId;
    }

    public User() {

    }

    public void hashPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
