package com.wiredbraincoffee.models;

import lombok.Data;

@Data
public class ProductEvent {
  private Long eventId;
  private String eventType;

  public ProductEvent() {}

  public ProductEvent(Long eventId, String eventType) {
    this.eventId = eventId;
    this.eventType = eventType;
  }

  @Override
  public String toString() {
    return "ProductEvent{" +
        "eventId=" + eventId +
        ", eventType='" + eventType + '\'' +
        '}';
  }
}
