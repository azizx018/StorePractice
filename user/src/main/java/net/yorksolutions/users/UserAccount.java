package net.yorksolutions.users;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
     Long id;

    final private String username;
    final private String password;
    final private Boolean isAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return username.equals(that.username) && password.equals(that.password) && isAdmin.equals(that.isAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isAdmin);
    }

    public UserAccount(String username, String password, Boolean isAdmin){

        this.isAdmin = isAdmin;
        this.username = username;
        this.password = password;

    }

    public UserAccount() {
         username = null;
         password = null;
         isAdmin = null;

    }
//
//    public Long getId() {
//        return id;
//    }
    public void setId(Long id) {
        this.id = id;
    }
}
