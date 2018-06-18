package com.wiredbraincoffee;

import java.util.Arrays;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxTest extends WiredBrainCoffeeApplicationTests {

  @Test
  public void firstFlux() {
    Flux.just("A", "B", "C")
        .log()
        .subscribe();
  }

  @Test
  public void fluxFromIterable() {
    Flux.fromIterable(Arrays.asList("A", "B", "C"))
        .log()
        .subscribe();
  }

  @Test
  public void fluxFromRange() {
    Flux.range(10, 5)
        .log()
        .subscribe();
  }

  @Test
  public void fluxRequest() {
    Flux.range(1, 5)
        .log()
        .subscribe(
            null,
            null,
            null,
            // request relieves backpressure
            s -> s.request(3)
        );
  }

}
