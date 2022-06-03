package net.yorksolutions.storebe;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/")

public class ProductController {
    private ProductService productService;

    @GetMapping("/viewAllProducts")
    Iterable<ProductAccount> viewAllProducts (@RequestParam UUID requestingUserToken) {
       // authorizationService.isAuthorized(requestingUserToken);
        return productService.viewAllProducts(requestingUserToken);

    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
