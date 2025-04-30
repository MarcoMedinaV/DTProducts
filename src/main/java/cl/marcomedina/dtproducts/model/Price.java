package cl.marcomedina.dtproducts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "prices")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Price {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    // ---

    @Column(name = "value", nullable = false)
    private Integer value;

    // ---
    
    @JoinColumn(name = "product_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Product product;

    // ---
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}