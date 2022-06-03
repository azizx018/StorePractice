package net.yorksolutions.storebe;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    String productName;


    public ProductAccount(String productName){
        this.productName = productName;

    }

    public ProductAccount() {
        productName = null;
    }
//    public ProductAccount() {
//        productName = null;
//    }
}
