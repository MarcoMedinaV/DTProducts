package cl.marcomedina.dtproducts.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class ProductDTO {
	private String name;
    
	private List<Long> colors;
	
    private List<Long> prices;
 
	public List<Long> getPrices() {
		return prices;
	}
	public void setPrices(List<Long> list) {
		this.prices = list;
	}
	public List<Long> getColors() {
		return colors;
	}
	public void setColors(List<Long> colors) {
		this.colors = colors;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}