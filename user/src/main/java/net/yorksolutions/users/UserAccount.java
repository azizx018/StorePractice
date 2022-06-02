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

    public String username;
    final private String password;
    final private Boolean isOwner;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return username.equals(that.username) && password.equals(that.password) && isOwner.equals(that.isOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, isOwner);
    }

    public UserAccount(String username, String password, Boolean isOwner){

        this.isOwner = isOwner;
        this.username = username;
        this.password = password;

    }

    public UserAccount() {
         username = null;
         password = null;
         isOwner = null;

    }
//
//    public Long getId() {
//        return id;
//    }
    public void setId(Long id) {
        this.id = id;
    }
}
