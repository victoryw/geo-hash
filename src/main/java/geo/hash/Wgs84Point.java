package geo.hash;

import lombok.NonNull;
import org.locationtech.spatial4j.context.SpatialContext;
import org.locationtech.spatial4j.shape.Point;
import org.locationtech.spatial4j.shape.impl.PointImpl;

import java.math.BigDecimal;


public class Wgs84Point {
    private final BigDecimal lat;
    private final BigDecimal lng;

    public Wgs84Point(BigDecimal lng, BigDecimal lat) {

        this.lat = lat;
        this.lng = lng;
    }

    @NonNull
     public BigDecimal getLat() {
        return lat;
    }

    @NonNull
    public BigDecimal getLng() {
        return lng;
    }

    public Point toSpatial4jPoint() {
        return new PointImpl(lng.doubleValue(), lat.doubleValue(), SpatialContext.GEO);
    }
}
