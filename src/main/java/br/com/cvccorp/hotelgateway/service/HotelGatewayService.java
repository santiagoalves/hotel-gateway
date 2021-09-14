package br.com.cvccorp.hotelgateway.service;

import br.com.cvccorp.hotelgateway.ServiceException;
import br.com.cvccorp.hotelgateway.client.HotelClient;
import br.com.cvccorp.hotelgateway.client.bean.HotelBean;
import br.com.cvccorp.hotelgateway.client.bean.HotelRoomBean;
import br.com.cvccorp.hotelgateway.service.bean.TripBean;
import br.com.cvccorp.hotelgateway.service.bean.TripPriceBean;
import br.com.cvccorp.hotelgateway.service.bean.TripRoomBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelGatewayService {

    private static final BigDecimal kickback = BigDecimal.valueOf(0.7);

    private final HotelClient client;

    public HotelGatewayService(final HotelClient client) {
        this.client = client;
    }

    @Cacheable("HotelGatewayService.findByCityCode")
    public List<TripBean> findByCityCode(
            final Long cityCode,
            final int totalDays,
            final int totalAdults,
            final int totalChildren
    ) {
        validate(
                totalDays,
                totalAdults,
                totalChildren
        );

        final var hotels = client.findByCityCode(cityCode);

        return wrapWithKickback(
                hotels,
                totalDays,
                totalAdults,
                totalChildren
        );
    }

    @Cacheable("HotelGatewayService.findByHotelId")
    public List<TripBean> findByHotelId(
            final Long hotelId,
            final int totalDays,
            final int totalAdults,
            final int totalChildren
    ) {
        validate(
                totalDays,
                totalAdults,
                totalChildren
        );

        final var hotels = client.findByHotelId(hotelId);

        return wrapWithKickback(
                hotels,
                totalDays,
                totalAdults,
                totalChildren
        );
    }

    private void validate(
            final int totalDays,
            final int totalAdults,
            final int totalChildren
    ) {
        if (totalDays < 1) {
            throw new ServiceException("totalDays must be at least one day");
        }
        if (totalAdults < 1) {
            throw new ServiceException("At least one adult must be informed");
        }

        if (totalChildren < 0) {
            throw new ServiceException("Invalid children value");
        }
    }

    private List<TripBean> wrapWithKickback(
            final List<HotelBean> hotels,
            final int totalDays,
            final int totalAdults,
            final int totalChildren
    ) {
        return hotels.stream().map(
                hotel -> new TripBean(
                        hotel.id,
                        hotel.cityName,
                        calculateRooms(
                                hotel.rooms,
                                totalDays,
                                totalAdults,
                                totalChildren
                        )
                )
        ).collect(Collectors.toUnmodifiableList());
    }

    private List<TripRoomBean> calculateRooms(
            final List<HotelRoomBean> rooms,
            final int totalDaysInHotel,
            final int totalAdults,
            final int totalChildren
    ) {
      /**
       * (pricePerDayAdult * totalAdults + pricePerDayChild * totalChild) * totalDays / kickback
       */
        return rooms.stream().map(room -> {
                    final var pricePerDayAdult = room.price.adult;
                    final var pricePerDayChild = room.price.child;
                    final var totalAdult = pricePerDayAdult * (double) (totalDaysInHotel + totalAdults);
                    final var totalChild = pricePerDayChild * (double) (totalDaysInHotel + totalChildren);
                    final var totalPrice = BigDecimal
                            .valueOf(totalAdult + totalChild)
                            .divide(kickback, 2, RoundingMode.HALF_UP);
                    return new TripRoomBean(
                            room.roomId,
                            room.categoryName,
                            totalPrice,
                            new TripPriceBean(
                                pricePerDayAdult * totalAdults,
                                pricePerDayChild * totalChildren
                            )
                    );
                }
        ).collect(Collectors.toUnmodifiableList());
    }
}
