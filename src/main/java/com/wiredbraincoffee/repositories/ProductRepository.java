package com.wiredbraincoffee.repositories;

import com.wiredbraincoffee.models.Product;
import org.reactivestreams.Publisher;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
  Flux<Product> findByName(Publisher<String> name);
}
