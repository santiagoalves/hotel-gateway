package br.com.cvccorp.hotelgateway.service.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class TripPriceBean {
    public final Double adult;
    public final Double child;

    @JsonCreator
    public TripPriceBean(
            @JsonProperty("pricePerDayAdult") final Double adult,
            @JsonProperty("pricePerDayChild") final Double child
    ) {
        this.adult = adult;
        this.child = child;
    }
}