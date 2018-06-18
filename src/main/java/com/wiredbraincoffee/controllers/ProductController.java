package com.wiredbraincoffee.controllers;

import com.wiredbraincoffee.models.Product;
import com.wiredbraincoffee.models.ProductEvent;
import com.wiredbraincoffee.repositories.ProductRepository;
import java.time.Duration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

  private ProductRepository productRepository;

  public ProductController(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @GetMapping
  public Flux<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @GetMapping("{id}")
  public Mono<ResponseEntity<Product>> getProduct(@PathVariable String id) {
    return productRepository
        .findById(id)
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Product> saveProduct(@RequestBody Product product) {
    return productRepository.save(product);
  }

  @PutMapping("{id}")
  public Mono<ResponseEntity<Product>> updateProduct(@PathVariable String id, @RequestBody Product updated) {
    return productRepository
        .findById(id)
        .flatMap(existing -> {
          existing.setPrice(updated.getPrice());
          existing.setName(updated.getName());
          return productRepository.save(existing);
        })
        .map(ResponseEntity::ok)
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping("{id}")
  public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
    return productRepository
        .findById(id)
        .flatMap(existing -> productRepository.delete(existing))
        .then(Mono.just(ResponseEntity.ok().<Void>build()))
        .defaultIfEmpty(ResponseEntity.notFound().build());
  }

  @DeleteMapping
  public Mono<Void> deleteAll() {
    return productRepository.deleteAll();
  }

  @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ProductEvent> getProductEvents() {
    return Flux
        .interval(Duration.ofSeconds(1))
        .map(val -> new ProductEvent(val, "Product Event"));
  }

}
