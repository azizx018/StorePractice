package net.yorksolutions.storebe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class ProductControllerTests {

    @LocalServerPort
    int port;

    @Autowired
    ProductController productController;

    @Mock
    ProductService productService;

    @BeforeEach
    void setup() {
        productController.setProductService(productService);
    }

    @Test
    void itShouldRespondOkWhenLoggedinUserRequestsViewAllProducts() {
        TestRestTemplate rest = new TestRestTemplate();
        final UUID requestingUserToken = UUID.randomUUID();
        String url = "http://localhost:" + port + "/viewAllProducts?requestingUserToken=" + requestingUserToken;
        ProductAccount [] expected = new ProductAccount []{};
        //doThrow(new ResponseStatusException(HttpStatus.OK)).when(productService).viewAllProducts(requestingUserToken);
        when(productService.viewAllProducts(requestingUserToken)).thenReturn(List.of(expected));
        final ResponseEntity<ProductAccount[]> response = rest.getForEntity(url, ProductAccount[].class);
        //final ResponseEntity<Void> response = rest.getForEntity(url, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(List.of(expected).equals(List.of(response.getBody())));
    }

}
