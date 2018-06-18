package com.wiredbraincoffee;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RouterFunctions.nest;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import com.wiredbraincoffee.handlers.ProductHandler;
import com.wiredbraincoffee.models.Product;
import com.wiredbraincoffee.repositories.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
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

  @Bean
  RouterFunction<ServerResponse> routes(ProductHandler handler) {
//	  return route(GET("/products").and(accept(APPLICATION_JSON)), handler::getProducts)
//        .andRoute(POST("/products").and(accept(APPLICATION_JSON)), handler::saveProduct)
//        .andRoute(DELETE("/products").and(accept(APPLICATION_JSON)), handler::deleteAll)
//        .andRoute(GET("/products/events").and(accept(TEXT_EVENT_STREAM)), handler::getProductEvents)
//        .andRoute(GET("/products/{id}").and(accept(APPLICATION_JSON)), handler::getProduct)
//        .andRoute(PUT("/products/{id}").and(contentType(APPLICATION_JSON)), handler::updateProduct)
//        .andRoute(DELETE("/products/{id}").and(accept(APPLICATION_JSON)), handler::deleteProduct);
    return nest(path("/products)"),
          nest(accept(APPLICATION_JSON).or(contentType(APPLICATION_JSON)).or(accept(TEXT_EVENT_STREAM)),
            route(GET("/"), handler::getProducts)
              .andRoute(POST("/"), handler::saveProduct)
              .andRoute(DELETE("/"), handler::deleteAll)
              .andRoute(GET("/events"), handler::getProductEvents)
              .andNest(path("/{id}"),
                route(GET("/"), handler::getProduct)
                .andRoute(PUT("/"), handler::updateProduct)
                .andRoute(DELETE("/"), handler::deleteProduct)
              )
          )
        );
  }
}
