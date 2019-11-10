package geo.hash;

import com.google.common.collect.ImmutableList;
import geo.hash.geohash.solution.Wgs84Point;
import geo.hash.fixture.PointTestRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class NearByTest {

    private static Wgs84Point standByPoint;

    @BeforeAll
    static void setUp() {
        standByPoint = new Wgs84Point(new BigDecimal("-0.165664"), new BigDecimal("51.507263"));
    }

    @Test void should_find_point_in_the_same_hash() {
        final var sameHashPoint = new Wgs84Point(new BigDecimal("-0.16567469"),
                new BigDecimal("51.50727510"));
        final var nearBy = new NearBy(new PointTestRepo(sameHashPoint));

        final var nearByPoints = nearBy.get(standByPoint, 3);

        assertIterableEquals(nearByPoints, ImmutableList.of(sameHashPoint));
    }

    @Test void should_find_right_point_in_the_near_hash() {
        final var sameHashPoint = new Wgs84Point(new BigDecimal("-0.1656469246"),
                new BigDecimal("51.50728791865268"));
        final var nearBy = new NearBy(new PointTestRepo(sameHashPoint));

        final var nearByPoints = nearBy.get(standByPoint, 4);

        assertIterableEquals(nearByPoints, ImmutableList.of(sameHashPoint));
    }

    @Test void should_filter_near_point_non_meet_distance() {
        final var sameHashPoint = new Wgs84Point(
                new BigDecimal("-0.16571760"),
                new BigDecimal("51.50727510"));
        final var nearBy = new NearBy(new PointTestRepo(sameHashPoint));

        final var nearByPoints = nearBy.get(standByPoint, 3);

        assertEquals(nearByPoints.size(), 0);
    }
}
