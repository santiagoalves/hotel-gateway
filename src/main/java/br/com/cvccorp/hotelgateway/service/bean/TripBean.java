package br.com.cvccorp.hotelgateway.service.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TripBean {
    public final Long id;
    public final String cityName;
    public final List<TripRoomBean> rooms;

    @JsonCreator
    public TripBean(
            @JsonProperty("id") final Long id,
            @JsonProperty("cityName") final String cityName,
            @JsonProperty("rooms") final List<TripRoomBean> rooms
    ) {
        this.id = id;
        this.cityName = cityName;
        this.rooms = rooms;
    }
}