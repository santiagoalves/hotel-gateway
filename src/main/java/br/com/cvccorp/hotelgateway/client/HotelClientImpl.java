package br.com.cvccorp.hotelgateway.client;

import br.com.cvccorp.hotelgateway.ServiceException;
import br.com.cvccorp.hotelgateway.client.bean.HotelBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
public class HotelClientImpl implements HotelClient {
    private static final TypeReference<List<HotelBean>> typeReference = new TypeReference<>() {
    };

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    @Cacheable("HotelClientImpl.findByCityCode")
    public List<HotelBean> findByCityCode(Long cityCode) {
        return requestHotelBeanList("https://cvcbackendhotel.herokuapp.com/hotels/avail/" + cityCode);
    }

    @Override
    @Cacheable("HotelClientImpl.findByHotelId")
    public List<HotelBean> findByHotelId(Long hotelId) {
        return requestHotelBeanList("https://cvcbackendhotel.herokuapp.com/hotels/" + hotelId);
    }

    private List<HotelBean> requestHotelBeanList(final String path) {
        final HttpsURLConnection connection;
        try {
            final var url = new URL(path);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == OK.value()) {
                return mapper.readValue(connection.getInputStream(), typeReference);
            }
            return Collections.emptyList();
        } catch (IOException ex) {
            throw new ServiceException(ex);
        }
    }
}