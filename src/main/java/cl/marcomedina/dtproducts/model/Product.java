package cl.marcomedina.dtproducts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    // ---

    @Column(name = "name", nullable = false)
    private String name;

    // ---
    
    @OneToMany(
    	mappedBy = "product",
    	cascade = CascadeType.ALL,
    	fetch = FetchType.LAZY
    )
    @JsonManagedReference
    private List<Price> prices = new ArrayList<>();
    
    // ---

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "products_colors",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    @JsonManagedReference
    private List<Color> colors = new ArrayList<>();

    // ---
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<Price> getPrices() { return prices; }
    public void setPrices(List<Price> prices) { this.prices = prices; }

    public List<Color> getColors() { return colors; }
    public void setColors(List<Color> colors) { this.colors = colors; }
}