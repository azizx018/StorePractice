package net.yorksolutions.storebe;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTests {
    @InjectMocks
    @Spy
    ProductService productService;

    @Mock
    ProductAccountRepository productRecordRepository;
    ProductAccount product1 = new ProductAccount("boots");
    ProductAccount product2 = new ProductAccount("coat");

    @Test
    void itShouldReturnAllProducts(){
        final ArrayList<ProductAccount> allProductList = new ArrayList<>();
        allProductList.add(product1);
        allProductList.add(product2);
        when(productRecordRepository.findAll()).thenReturn(allProductList);
        final Iterable <ProductAccount> allProducts = productService.viewAllProducts();
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