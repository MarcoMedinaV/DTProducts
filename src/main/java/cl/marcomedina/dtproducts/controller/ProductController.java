package cl.marcomedina.dtproducts.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.marcomedina.dtproducts.dto.ProductDTO;
import cl.marcomedina.dtproducts.model.Color;
import cl.marcomedina.dtproducts.model.Price;
import cl.marcomedina.dtproducts.model.Product;
import cl.marcomedina.dtproducts.repository.ColorRepository;
import cl.marcomedina.dtproducts.repository.PriceRepository;
import cl.marcomedina.dtproducts.repository.ProductRepository;
import jakarta.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	private static final Logger LOGGER = LogManager.getLogger(ProductController.class.getName());
	
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private PriceRepository priceRepository;
    
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return
        	productRepository.findById(id)
        		.map(product -> ResponseEntity.ok(product))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProductDTO request) {
        try {
        	if (request.getName().equals("") || request.getName().length() > 255) {
        		return ResponseEntity.badRequest().body("Invalid name: name can't be empty and should have less than 256 characters.");
        	}
        	
            // IMPORTANT: Check if colors and prices do actually exist!
            List<Color> colors = colorRepository.findAllById(request.getColors());
            if (colors.size() != request.getColors().size()) {
                return ResponseEntity.badRequest().body("Please, select a valid color.");
            }

            List<Price> prices = priceRepository.findAllById(request.getPrices());
            if (prices.size() != request.getPrices().size()) {
                return ResponseEntity.badRequest().body("Please select a valid price.");
            }

            // ---
            
            Product newProduct = new Product();
            newProduct.setName(request.getName());
            newProduct.setColors(colors);
            newProduct.setPrices(prices);

            return ResponseEntity.ok().body(
            	"Product created successfully. ID: " + productRepository.save(newProduct).getId()
            );
        } catch (Exception e) {
        	/* NOTE:
        	 * It's a mandatory practice to keep exception messages away from the front end!
        	 */
        	LOGGER.error("Could not create product, please check log:" + e.getMessage());
        	
            return 
            	ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            		.body("Could not create product, please check log. ");
            
            
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
    	@PathVariable Long id, 
    	@RequestBody ProductDTO request
    ) {
        try {
        	if (request.getName().equals("") || request.getName().length() > 255) {
        		return ResponseEntity.badRequest().body("Invalid name: name can't be empty and should have less than 256 characters.");
        	}
        	
        	// IMPORTANT: Check if colors and prices do actually exist!
            Optional<Product> foundProduct = productRepository.findById(id);
            if (!foundProduct.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Product with ID " + id + " not found.");
            }

            List<Color> colors = colorRepository.findAllById(request.getColors());
            if (colors.size() != request.getColors().size()) {
                return ResponseEntity.badRequest().body("Please select a valid color.");
            }

            List<Price> prices = priceRepository.findAllById(request.getPrices());
            if (prices.size() != request.getPrices().size()) {
                return ResponseEntity.badRequest().body("Please select a valid price.");
            }

            Product updatedProduct = foundProduct.get();
            
            priceRepository.deleteAll(updatedProduct.getPrices());

            updatedProduct.setName(request.getName());
            updatedProduct.setColors(colors);
            updatedProduct.setPrices(prices);

            productRepository.save(updatedProduct);

            LOGGER.info("Product updated successfully. ID: " + updatedProduct.getId());
            
            return ResponseEntity.ok().body("Product updated successfully. ID: " + updatedProduct.getId());
        } catch (Exception e) {
        	LOGGER.error("Could not create product, please check log,");
        	
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Could not update product: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            LOGGER.info("Product deleted successfully. ID: " + id);
            
            return ResponseEntity.ok().build();
        }
        
        LOGGER.info("Product deletion attempt, invalid ID was used: " + id);
        
        return ResponseEntity.notFound().build();
    }
}