package geo.hash;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeoHashTest {

    @Test
    void should_get_the_geo_hash_level1() throws UnsupportedEncodingException {
        final var position = new Wgs84Point(new BigDecimal("116.40382"), new BigDecimal("39.918118"));

        final GeoHash geoHash = new GeoHash(1, position);

        assertEquals("w", geoHash.value());
    }

    @Test
    void should_get_the_geo_hash_level2() throws UnsupportedEncodingException {
        final var position = new Wgs84Point(new BigDecimal("116.40382"), new BigDecimal("39.918118"));

        final GeoHash geoHash = new GeoHash(2, position);

        assertEquals("wx", geoHash.value());
    }

    @Test
    void should_get_the_geo_hash_level10() throws UnsupportedEncodingException {
        final var position = new Wgs84Point(new BigDecimal("116.40382"), new BigDecimal("39.918118"));

        final GeoHash geoHash = new GeoHash(10, position);

        assertEquals("wx4g0ffevs", geoHash.value());
    }

}
