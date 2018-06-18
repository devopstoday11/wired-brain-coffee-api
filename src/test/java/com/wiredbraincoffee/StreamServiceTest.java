package com.wiredbraincoffee;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import com.wiredbraincoffee.services.StreamService;
import java.util.List;
import org.junit.Test;

public class StreamServiceTest extends WiredBrainCoffeeApplicationTests {

  @Test
  public void succeedFilterNumericStrings() {
    List<String> input = asList("hello", "th3r3", "i'm", "m1tch", "mitch");
    List<String> actual = StreamService.filterNumericStrings(input);
    List<String> expected = asList("hello", "i'm", "mitch");
    assertThat(actual, is(expected));
  }

  @Test
  public void failFilterNumericStrings() {
    List<String> input = asList("hello", "th3r3", "i'm", "m1tch", "mitch");
    List<String> actual = StreamService.filterNumericStrings(input);
    List<String> expected = asList("th3r3", "m1tch");
    assertThat(actual, is(not(expected)));
  }

  @Test
  public void succeedShouldMapStringsToUpperCase() {
    List<String> input = asList("This", "is", "java", "8");
    List<String> actual = StreamService.mapToUpperCase(input);
    List<String> expected = asList("THIS", "IS", "JAVA", "8");
    assertThat(actual, is(expected));
  }

  @Test
  public void failShouldMapStringsToUpperCase() {
    List<String> input = asList("This", "is", "java", "8");
    List<String> result = StreamService.mapToUpperCase(input);
    List<String> expected = asList("this", "is", "java", "8");
    assertThat(result, is(not(expected)));
  }

  @Test
  public void succeedShouldAggregateSum() {
    List<Integer> input = asList(1, 2, 3, 4, 5);
    int result = StreamService.aggregateSum(input);
    int expected = 15;
    assertEquals(result, expected);
  }

  @Test
  public void failShouldAggregateSum() {
    List<Integer> input = asList(1, 2, 3, 4, 5);
    int result = StreamService.aggregateSum(input);
    int expected = 0;
    assertNotEquals(result, expected);
  }

  @Test
  public void succeedShouldGetMinimumInt() {
    List<Integer> input = asList(3, 2, 5, 4, 1);
    int result = StreamService.getMinimum(input);
    int expected = 1;
    assertEquals(result, expected);
  }

  @Test
  public void failShouldGetMinimumInt() {
    List<Integer> input = asList(3, 2, 5, 4, 1);
    int result = StreamService.getMinimum(input);
    int expected = 2;
    assertNotEquals(result, expected);
  }

}
