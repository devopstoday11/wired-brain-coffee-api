package com.wiredbraincoffee;

import com.wiredbraincoffee.models.Product;
import com.wiredbraincoffee.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class WiredBrainCoffeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiredBrainCoffeeApplication.class, args);
	}

	@Bean
  CommandLineRunner init(ProductRepository repository) {
	  return args -> {
	    Flux<Product> productFlux = Flux.just(
	        new Product(null, "Latte", 2.99),
          new Product(null, "Decaf", 1.95),
          new Product(null, "Chai Tea", 3.49)
      );

	    productFlux
          .flatMap(repository::save)
          .thenMany(repository.findAll())
          .subscribe(System.out::println);
    };
  }
}
