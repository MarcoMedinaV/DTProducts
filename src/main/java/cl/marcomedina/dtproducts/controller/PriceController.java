package cl.marcomedina.dtproducts.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.marcomedina.dtproducts.model.Price;
import cl.marcomedina.dtproducts.repository.PriceRepository;

@RestController
@RequestMapping("/api/prices")
public class PriceController {
	/* Please consider:
	 * Due to time constrains, deeper validations and exception handling
	 * were not applied. Thank you for understanding.
	 * 
	 * For better validations, please check ProductController.
	 */

    private final PriceRepository priceRepository;

    public PriceController(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public List<Price> getAll() {
        return priceRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Price> getById(@PathVariable Long id) {
        return
        	priceRepository.findById(id)
        		.map(foundPrice -> ResponseEntity.ok(foundPrice))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Price create(@RequestBody Price price) {
        return priceRepository.save(price);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Price> update(
    	@PathVariable Long id,
    	@RequestBody Price putPrice
    ) {
        return priceRepository.findById(id)
        	.map(foundPrice -> {
        		foundPrice.setValue(putPrice.getValue());
        		foundPrice.setProduct(putPrice.getProduct());
	            
	            return ResponseEntity.ok(priceRepository.save(foundPrice));
	        }).orElse(
	        	ResponseEntity.notFound().build()
	        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (priceRepository.existsById(id)) {
            priceRepository.deleteById(id);
            
            return ResponseEntity.noContent().build();
        }
        
        return ResponseEntity.notFound().build();
    }
}