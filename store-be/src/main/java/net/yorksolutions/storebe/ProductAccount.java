package net.yorksolutions.storebe;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductAccount {
    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @JsonProperty ("productName")
    String productName;


    @JsonCreator
    public ProductAccount(@JsonProperty("productName") String productName){
        this.productName = productName;

    }

    public ProductAccount() {
        productName = null;
    }

}
