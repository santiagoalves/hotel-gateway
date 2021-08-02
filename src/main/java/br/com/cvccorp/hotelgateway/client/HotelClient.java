package br.com.cvccorp.hotelgateway.client;

import br.com.cvccorp.hotelgateway.client.bean.HotelBean;

import java.util.List;


public interface HotelClient {

    List<HotelBean> findByCityCode(Long cityCode);

    List<HotelBean> findByHotelId(Long hotelId);

}
