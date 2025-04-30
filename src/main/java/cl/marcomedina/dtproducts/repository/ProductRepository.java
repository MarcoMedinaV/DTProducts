package cl.marcomedina.dtproducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.marcomedina.dtproducts.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> { }