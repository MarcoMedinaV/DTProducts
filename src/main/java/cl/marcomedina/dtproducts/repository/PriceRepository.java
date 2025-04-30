package cl.marcomedina.dtproducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.marcomedina.dtproducts.model.Price;

public interface PriceRepository extends JpaRepository<Price, Long> { }