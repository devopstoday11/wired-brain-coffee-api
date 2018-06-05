package com.reactiveweb.demo;

import java.util.Arrays;
import org.junit.Test;
import reactor.core.publisher.Flux;

public class FluxTest {

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

}
