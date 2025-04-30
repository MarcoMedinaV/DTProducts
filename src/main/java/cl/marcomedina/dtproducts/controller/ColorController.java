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

import cl.marcomedina.dtproducts.model.Color;
import cl.marcomedina.dtproducts.repository.ColorRepository;

@RestController
@RequestMapping("/api/colors")
public class ColorController {
	/* Please consider:
	 * Due to time constrains, deeper validations and exception handling
	 * were not applied. Thank you for understanding.
	 * 
	 * For better validations, please check ProductController.
	 */

    private final ColorRepository colorRepository;

    public ColorController(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @GetMapping
    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Color> getById(@PathVariable Long id) {
        return 
        	colorRepository.findById(id)
                .map(foundColor -> ResponseEntity.ok(foundColor))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Color create(@RequestBody Color color) {
        return colorRepository.save(color);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Color> update(
    	@PathVariable Long id,
    	@RequestBody Color color
    ) {
        return 
        	colorRepository.findById(id)
	        	.map(foundColor -> {
	        		foundColor.setLabel(color.getLabel());
		            
		            return ResponseEntity.ok(colorRepository.save(foundColor));
		        }).orElse(
		        	ResponseEntity.notFound().build()
		        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (colorRepository.existsById(id)) {
            colorRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}