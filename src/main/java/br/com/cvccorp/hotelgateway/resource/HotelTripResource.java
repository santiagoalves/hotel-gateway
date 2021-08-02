package br.com.cvccorp.hotelgateway.resource;

import br.com.cvccorp.hotelgateway.ServiceException;
import br.com.cvccorp.hotelgateway.service.HotelGatewayService;
import br.com.cvccorp.hotelgateway.service.bean.TripBean;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
@RequestMapping("/v1/country/brazil")
public class HotelTripResource {

    private final HotelGatewayService service;

    public HotelTripResource(final HotelGatewayService service) {
        this.service = service;
    }

    @GetMapping("/city/{cityCode}")
    public List<TripBean> listByCityCode(
            @PathVariable final Long cityCode,
            @RequestParam final LocalDate checkIn,
            @RequestParam final LocalDate checkOut,
            @RequestParam final int totalAdults,
            @RequestParam final int totalChildren
    ) {
        return service.findByCityCode(
                cityCode,
                computeTotalDays(checkIn, checkOut),
                totalAdults,
                totalChildren
        );
    }

    @GetMapping("/hotel/{hotelId}")
    public List<TripBean> fetchTrip(
            @PathVariable final Long hotelId,
            @RequestParam final LocalDate checkIn,
            @RequestParam final LocalDate checkOut,
            @RequestParam final int totalAdults,
            @RequestParam final int totalChildren
    ) {
        return service.findByHotelId(
                hotelId,
                computeTotalDays(checkIn, checkOut),
                totalAdults,
                totalChildren
        );
    }

    private int computeTotalDays(
            final LocalDate checkIn,
            final LocalDate checkOut
    ) {
        if (checkIn.isBefore(LocalDate.now())) {
            throw new ServiceException("CheckIn must be at least today");
        }

        if (checkOut.isEqual(checkIn)) {
            throw new ServiceException("CheckOut must be at least one day ahead CheckIn");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new ServiceException("CheckOut must be greater than CheckIn");
        }
        return (int) ChronoUnit.DAYS.between(checkIn, checkOut);
    }
}
