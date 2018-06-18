package com.wiredbraincoffee;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wiredbraincoffee.controllers.ProductController;
import com.wiredbraincoffee.models.Product;
import com.wiredbraincoffee.models.ProductEvent;
import com.wiredbraincoffee.repositories.ProductRepository;
import java.util.List;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductControllerTests {
  private WebTestClient client;
  private List<Product> expectedList;

  @Autowired
  private ProductRepository repository;

  @BeforeEach
  void beforeEach() {
    System.out.println("INITING CLIENT");
    this.client = WebTestClient
        .bindToController(new ProductController(repository))
        .configureClient()
        .baseUrl("/products")
        .build();

    this.expectedList = repository.findAll().collectList().block();
  }

  @Test
  public void testGetProducts() {
    beforeEach();
    this.client
        .get()
        .uri("/")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Product.class).isEqualTo(expectedList);
  }

  @Test
  public void testGetProductForValidId() {
    beforeEach();
    Product expected = expectedList.get(0);
    this.client
        .get()
        .uri("/{id}", expected.getId())
        .exchange()
        .expectStatus().isOk()
        .expectBody(Product.class).isEqualTo(expected);
  }

  @Test
  public void testGetProductForInvalidId() {
    beforeEach();
    this.client
        .get()
        .uri("/123456789")
        .exchange()
        .expectStatus().isNotFound();
  }

  @Test
  public void testProductEvents() {
    beforeEach();
    ProductEvent expected = new ProductEvent(0L, "Product Event");

    FluxExchangeResult<ProductEvent> result = this.client
        .get()
        .uri("/events")
        .accept(MediaType.TEXT_EVENT_STREAM)
        .exchange()
        .expectStatus().isOk()
        .returnResult(ProductEvent.class);

    StepVerifier.create(result.getResponseBody())
        .expectNext(expected)
        .expectNextCount(2)
        .consumeNextWith(event ->
            assertEquals(Long.valueOf(3), event.getEventId()))
        .thenCancel()
        .verify();
  }
}
