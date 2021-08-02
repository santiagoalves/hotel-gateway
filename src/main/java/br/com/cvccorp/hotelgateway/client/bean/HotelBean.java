package br.com.cvccorp.hotelgateway.client.bean;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class HotelBean {
    public final Long id;
    public final String name;
    public final Integer cityCode;
    public final String cityName;
    public final List<HotelRoomBean> rooms;

    @JsonCreator
    public HotelBean(
            @JsonProperty("id") final Long id,
            @JsonProperty("name") final String name,
            @JsonProperty("cityCode") final Integer cityCode,
            @JsonProperty("cityName") final String cityName,
            @JsonProperty("rooms") final List<HotelRoomBean> rooms
    ) {
        this.id = id;
        this.name = name;
        this.cityCode = cityCode;
        this.cityName = cityName;
        this.rooms = rooms;
    }

}