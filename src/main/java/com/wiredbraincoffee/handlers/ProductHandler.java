package com.wiredbraincoffee.handlers;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

import com.wiredbraincoffee.models.Product;
import com.wiredbraincoffee.models.ProductEvent;
import com.wiredbraincoffee.repositories.ProductRepository;
import java.time.Duration;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class ProductHandler {
  private ProductRepository productRepository;

  public ProductHandler(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  public Mono<ServerResponse> getProducts(ServerRequest request) {
    Flux<Product> products = productRepository.findAll();

    return ServerResponse
        .ok()
        .contentType(APPLICATION_JSON)
        .body(products, Product.class);
  }

  public Mono<ServerResponse> getProduct(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Product> productMono = productRepository.findById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return productMono
        .flatMap(product -> {
          return ServerResponse
              .ok()
              .contentType(APPLICATION_JSON)
              .body(fromObject(product));
        })
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> saveProduct(ServerRequest request) {
    Mono<Product> productMono = request.bodyToMono(Product.class);

    return productMono
        .flatMap(product -> {
          return ServerResponse
              .status(CREATED)
              .contentType(APPLICATION_JSON)
              .body(fromObject(productRepository.save(product)));
        });
  }

  public Mono<ServerResponse> updateProduct(ServerRequest request) {
    String id = request.pathVariable("id");
    Mono<Product> existingProductMono = productRepository.findById(id);
    Mono<Product> productMono = request.bodyToMono(Product.class);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return productMono
        .zipWith(existingProductMono, (product, existingProduct) -> {
          return new Product(existingProduct.getId(), product.getName(), product.getPrice());
        })
        .flatMap(product -> {
          return ServerResponse
              .status(CREATED)
              .contentType(APPLICATION_JSON)
              .body(fromObject(productRepository.save(product)));
        })
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> deleteProduct(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Product> productMono = productRepository.findById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return productMono
        .flatMap(existing -> {
          return ServerResponse
              .ok()
              .build(productRepository.delete(existing));
        })
        .switchIfEmpty(notFound);
  }

  public Mono<ServerResponse> deleteAll(ServerRequest request) {
    return ServerResponse
        .ok()
        .build(productRepository.deleteAll());
  }

  public Mono<ServerResponse> getProductEvents(ServerRequest request) {
    Flux<ProductEvent> eventsFlux = Flux
        .interval(Duration.ofSeconds(1))
        .map(val -> new ProductEvent(val, "Product Event"));

    return ServerResponse
        .ok()
        .contentType(MediaType.TEXT_EVENT_STREAM)
        .body(eventsFlux, ProductEvent.class);
  }
}
