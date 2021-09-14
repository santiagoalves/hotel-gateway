package br.com.cvccorp.hotelgateway.service.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class TripRoomBean {
    public final Integer roomId;
    public final String categoryName;
    public final BigDecimal totalPrice;
    public final TripPriceBean priceDetail;

    @JsonCreator
    public TripRoomBean(
            @JsonProperty("roomID") final Integer roomId,
            @JsonProperty("categoryName") final String categoryName,
            @JsonProperty("totalPrice") final BigDecimal totalPrice,
            @JsonProperty("priceDetail") final TripPriceBean priceDetail
    ) {
        this.roomId = roomId;
        this.categoryName = categoryName;
        this.totalPrice = totalPrice;
        this.priceDetail = priceDetail;
    }
}
