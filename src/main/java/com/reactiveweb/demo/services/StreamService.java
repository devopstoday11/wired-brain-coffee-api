package com.reactiveweb.demo.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamService {

  public static List<String> mapToUpperCase(List<String> list) {
    Stream<String> stream = list.stream();
    return stream
        .map(String::toUpperCase)
        .collect(Collectors.toList());
  }

  public static int aggregateSum(List<Integer> list) {
    Stream<Integer> stream = list.stream();
    return stream.reduce((accumulator, value) -> accumulator + value).get();
  }

  public static <T> T getMinimum(List<T> list) {
    Stream<T> stream = list.stream();
    return stream
        .min(Comparator.comparing(item -> item.toString()))
        .get();
  }

}
