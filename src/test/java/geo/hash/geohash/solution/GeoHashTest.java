package geo.hash.geohash.solution;

import com.google.common.collect.ImmutableList;
import geo.hash.geohash.solution.Wgs84Point;
import geo.hash.geohash.solution.GeoHash;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class GeoHashTest {

    @Test
    void should_get_the_geo_hash_level1() {
        final var position = new Wgs84Point(new BigDecimal("116.40382"), new BigDecimal("39.918118"));

        final GeoHash geoHash = new GeoHash(1, position);

        assertEquals("w", geoHash.value());
    }

    @Test
    void should_get_the_geo_hash_level2() {
        final var position = new Wgs84Point(new BigDecimal("116.40382"), new BigDecimal("39.918118"));

        final GeoHash geoHash = new GeoHash(2, position);

        assertEquals("wx", geoHash.value());
    }

    @Test
    void should_get_the_geo_hash_level10() {
        final var position = new Wgs84Point(new BigDecimal("116.320583"), new BigDecimal("39.984629"));

        final GeoHash geoHash = new GeoHash(10, position);

        assertEquals("wx4eqyyp65", geoHash.value());
    }

    @Test
    void should_get_the_point_around_hashes() {
//        51.507263, -0.165664
        final var position = new Wgs84Point(new BigDecimal("-0.165664"), new BigDecimal("51.507263"));

        final GeoHash geoHash = new GeoHash(9, position);
        assertEquals("gcpvh0x7e", geoHash.value());
        final var neighborGeoHashValues = geoHash.getNeighborGeoHashValues();
        assertIterableEquals(neighborGeoHashValues, ImmutableList.of(
                "gcpvh0x7g",
                "gcpvh0x7u",
                "gcpvh0x7s",
                "gcpvh0x7k",
                "gcpvh0x77",
                "gcpvh0x76",
                "gcpvh0x7d",
                "gcpvh0x7f"
        ));
    }

}
