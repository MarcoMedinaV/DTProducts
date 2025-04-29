package cl.marcomedina.prueba.model;

import jakarta.persistence.*;

@Entity
@Table(name = "prices")
public class Price {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value", nullable = false)
    private Integer value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getValue() { return value; }
    public void setValue(Integer value) { this.value = value; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}