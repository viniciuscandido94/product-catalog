package br.com.rest.compasso.repository;

import br.com.rest.compasso.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
