package cl.marcomedina.prueba.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.marcomedina.prueba.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {}