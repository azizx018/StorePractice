package net.yorksolutions.storebe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    @Spy
    ProductService productService;

    @Mock
    ProductAccountRepository productAccountRepository;
    ProductAccount product1 = new ProductAccount("boots");
    ProductAccount product2 = new ProductAccount("coat");

    @Mock
    RestTemplate rest;

    @Test
    void itShouldThrowUnauthWhenStatusIsUnauth() {
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:8081/isAuthorized?token=" + token;
        when(rest.getForEntity(url, UUID.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> productService.checkAuthorized(token));
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatus());
    }
    @Test
    void itShouldThrowInternalServerErrorWhenOtherStatusofCheckAuthorizedIsOther() {
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:8081/isAuthorized?token=" + token;
        when(rest.getForEntity(url, UUID.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
        final ResponseStatusException exception = assertThrows(ResponseStatusException.class,
                ()-> productService.checkAuthorized(token));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getStatus());
    }

    @Test
    void itShouldNotThrowWhenOtherStatusIsOk() {
        final UUID token = UUID.randomUUID();
        String url = "http://localhost:8081/isAuthorized?token=" + token;
        when(rest.getForEntity(url, UUID.class))
                .thenReturn(new ResponseEntity<>(HttpStatus.OK));
        assertDoesNotThrow(() ->productService.checkAuthorized(token));
    }

    @Test
    void itShouldReturnAllProductsWhenTokenIsGood(){
        final UUID token = UUID.randomUUID();
        final ArrayList<ProductAccount> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        doNothing().when(productService).checkAuthorized(token);
        when(productAccountRepository.findAll()).thenReturn(allProductList);
        final Iterable <ProductAccount> allProducts = productService.viewAllProducts(token);
        assertEquals(allProductList, allProducts);

    }
//    @Test
//    void itShouldAddAProductWhenOwnerCallsAddProductAndProductNameIsUnique() {
//        final String productName = "hamper";
//
//        final ProductRecord expected = new ProductRecord(productName);
//        ArgumentCaptor<ProductRecord> captor = ArgumentCaptor.forClass(ProductRecord.class);
//        when(productRecordRepository.save(captor.capture())).thenReturn(new ProductRecord());
//
//        when(productRecordRepository.findByProductName(productName)).thenReturn(Optional.empty());
//        productService.addProduct(productName);
//        assertEquals(expected,captor.getValue());
//
//
//    }

}


//add
//delete
//edit