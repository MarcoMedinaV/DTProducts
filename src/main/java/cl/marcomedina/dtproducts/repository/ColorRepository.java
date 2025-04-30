package cl.marcomedina.dtproducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.marcomedina.dtproducts.model.Color;

public interface ColorRepository extends JpaRepository<Color, Long> { }