package br.com.cvccorp.hotelgateway.client.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class HotelRoomPriceBean {
    public final Double adult;
    public final Double child;

    @JsonCreator
    public HotelRoomPriceBean(
            @JsonProperty("adult") final Double adult,
            @JsonProperty("child") final Double child
    ) {
        this.adult = adult;
        this.child = child;
    }

}