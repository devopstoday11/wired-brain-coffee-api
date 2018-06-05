package com.reactiveweb.demo;

import org.junit.Test;
import reactor.core.publisher.Mono;

public class MonoTest {

  @Test
  public void firstMono() {
    Mono.just("A")
        .log()
        .subscribe();
  }

  @Test
  public void monoWithConsumer() {
    Mono.just("A")
        .log()
        .subscribe(System.out::println);
  }

  @Test
  public void monoWithDoOn() {
    Mono.just("A")
        .log()
        .doOnSubscribe(s -> System.out.println("Subscribed: " + s.toString()))
        .doOnRequest(r -> System.out.println("Request: " + r))
        .doOnSuccess(s -> System.out.println("Success: " + s))
        .subscribe(System.out::println);
  }

  @Test
  public void emptyMono() {
    // empty Mono is conceptually similar to returning void
    Mono.empty()
        .log()
        .subscribe();
  }

  @Test
  public void emptyCompleteConsumerMono() {
    Mono.empty()
        .log()
        .subscribe(
            System.out::println,
            null,
            () -> System.out.println("Empty complete Consumer finished successfully.")
        );
  }

  @Test
  public void errorRuntimeExceptionMono() {
    Mono.error(new RuntimeException())
        .log()
        .subscribe();
  }

  @Test
  public void errorExceptionMono() {
    Mono.error(new Exception())
        .log()
        .subscribe(
            System.out::println,
            e -> System.out.println("Error: " + e)
        );
  }

  @Test
  public void doOnErrorExceptionMono() {
    Mono.error(new Exception())
        .onErrorResume(e -> {
          System.out.println("Catching: " + e);
          return Mono.just("Returned from caught error.");
        })
        // .onErrorReturn() to return some value instead of a Mono
        .log()
        .subscribe();
  }

}
