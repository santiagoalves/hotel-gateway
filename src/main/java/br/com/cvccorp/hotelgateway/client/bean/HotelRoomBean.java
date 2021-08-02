package br.com.cvccorp.hotelgateway.client.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelRoomBean {
    public final Integer roomId;
    public final String categoryName;
    public final HotelRoomPriceBean price;

    @JsonCreator
    public HotelRoomBean(
            @JsonProperty("roomID") final Integer roomId,
            @JsonProperty("categoryName") final String categoryName,
            @JsonProperty("price") final HotelRoomPriceBean price
    ) {
        this.roomId = roomId;
        this.categoryName = categoryName;
        this.price = price;
    }
}