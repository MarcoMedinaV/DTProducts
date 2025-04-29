package cl.marcomedina.prueba.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.marcomedina.prueba.model.Price;
import cl.marcomedina.prueba.model.Product;
import cl.marcomedina.prueba.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAllproducts() {
        return productRepository.findAll();
    }
    
    @PostMapping
    public ResponseEntity<Product> saveProduct(@RequestBody Product product) {
        for (Price price : product.getPrices()) {
            price.setProduct(product);
        }

        Product saved = productRepository.save(product);
        return ResponseEntity.ok(saved);
    }
}