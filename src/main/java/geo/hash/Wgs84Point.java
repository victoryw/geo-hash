package geo.hash;

import java.math.BigDecimal;


public class Wgs84Point {
    private final BigDecimal lat;
    private final BigDecimal lng;

    public Wgs84Point(BigDecimal lng, BigDecimal lat) {

        this.lat = lat;
        this.lng = lng;
    }

    public BigDecimal getLat() {
        return lat;
    }

    public BigDecimal getLng() {
        return lng;
    }
}
