package br.com.cvccorp.hotelgateway;

import br.com.cvccorp.hotelgateway.client.HotelClient;
import br.com.cvccorp.hotelgateway.client.bean.HotelBean;
import br.com.cvccorp.hotelgateway.service.HotelGatewayService;
import br.com.cvccorp.hotelgateway.service.bean.TripBean;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class HotelGatewayServiceTest {

    private static final TypeReference<List<HotelBean>> typeReference = new TypeReference<>() {
    };

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();

    @Mock
    private HotelClient client;

    @InjectMocks
    private HotelGatewayService service;

    @BeforeEach
    void beforeEach() throws IOException {

        final var resourcePayload = Paths.get(
                "src",
                "test",
                "resources",
                "payload.json"
        );

        final var result = mapper.readValue(
                resourcePayload.toFile(),
                typeReference
        );
        when(client.findByCityCode(any())).thenReturn(result);
    }

    @Test
    void shouldGenerateTripBean() throws IOException {

        final var cityCode = 1032L;
        final var totalDays = 1;
        final var totalAdults = 1;
        final var totalChildren = 1;

        final var actualResult = service.findByCityCode(
                cityCode,
                totalDays,
                totalAdults,
                totalChildren
        );

        final var actualResponse = writer.writeValueAsString(actualResult);

        final var expectedResponse = writer.writeValueAsString(
                mapper.readValue(
                        Paths.get("src", "test", "resources", "response.json").toFile(),
                        new TypeReference<List<TripBean>>() {
                        }
                )
        );

        Assertions.assertEquals(expectedResponse, actualResponse);
    }
}
