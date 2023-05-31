package com.pedro.crud.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.crud.domain.product.Product;
import com.pedro.crud.domain.product.ProductRepository;
import com.pedro.crud.domain.product.RequestProduct;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductRepository repository;
	
	@GetMapping
	public ResponseEntity getAllProducts() {
		var allProducts = repository.findAll();
		return ResponseEntity.ok(allProducts);
	}
	
	@PostMapping
	public ResponseEntity addProducts(@RequestBody @Valid RequestProduct data) {
		Product product = new Product(data);
		repository.save(product);
//		System.out.println(data);
		return ResponseEntity.ok().build();
		
		
	}
	
	@PutMapping
	@Transactional
	public ResponseEntity updateProducts(@RequestBody @Valid RequestProduct data) {
		Optional<Product> optionalProduct = repository.findById(data.id());
		if(optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			product.setName(data.name());
			product.setPriceInCents(data.priceInCents());
			return ResponseEntity.ok(product);
		}else {
			throw new EntityNotFoundException();
		}
		
	}

}
