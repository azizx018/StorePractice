package net.yorksolutions.storebe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/")

public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(@NonNull ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/viewAllProducts")
    Iterable<ProductAccount> viewAllProducts (@RequestParam UUID token) {
        return productService.viewAllProducts(token);

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
