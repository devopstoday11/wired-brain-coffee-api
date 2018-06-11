package com.wiredbraincoffee;

import java.time.Duration;
import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class OperatorTest {

  @Test
  public void map() {
    Flux.range(1, 5)
        .map(this::multiplyByTen)
        .subscribe(System.out::println);
  }

  @Test
  public void flatMap() {
    Flux.range(1, 5)
        .flatMap(i -> Flux.range(i * 10, 3))
        .subscribe(System.out::println);
  }

  @Test
  public void flatMapMany() {
    Mono.just(3)
        .flatMapMany(i -> Flux.range(1, i))
        .subscribe(System.out::println);
  }

  @Test
  public void concat() throws InterruptedException {
    Flux<Integer> oneToFive = Flux.range(1, 5)
        .delayElements(Duration.ofMillis(200));

    Flux<Integer> sixToTen = Flux.range(6, 5)
        .delayElements(Duration.ofMillis(400));

    Flux.concat(oneToFive, sixToTen)
        .subscribe(System.out::println);

//    Thread.sleep(4000);
  }

  @Test
  public void merge() throws InterruptedException {
    Flux<Integer> oneToFive = Flux.range(1, 5)
        .delayElements(Duration.ofMillis(200));

    Flux<Integer> sixToTen = Flux.range(6, 5)
        .delayElements(Duration.ofMillis(400));

    Flux.merge(oneToFive, sixToTen)
        .subscribe(System.out::println);

//    Thread.sleep(4000);
  }

  @Test
  public void zip() {
    Flux<Integer> oneToFive = Flux.range(1, 5);
    Flux<Integer> sixToTen = Flux.range(6, 5);

    Flux.zip(oneToFive, sixToTen, this::concatElements)
        .subscribe(System.out::println);
  }

  public int multiplyByTen(int n) {
    return n * 10;
  }

  public <T> String concatElements(T a, T b) {
    return a.toString() + ", " + b.toString();
  }

}
