package geo.hash.geohash.solution;

import lombok.NonNull;

import java.math.BigDecimal;


public class Wgs84Point {
    private final BigDecimal lat;
    private final BigDecimal lng;

    public Wgs84Point(BigDecimal lng, BigDecimal lat) {
        this.lat = lat;
        this.lng = lng;
    }

    @NonNull BigDecimal getLat() {
        return lat;
    }

    @NonNull BigDecimal getLng() {
        return lng;
    }

    public BigDecimal distanceTo(Wgs84Point standByPoint) {
        return BigDecimal.valueOf(distFrom(this.lat.doubleValue(), this.lng.doubleValue(),
                standByPoint.lat.doubleValue(), standByPoint.lng.doubleValue())).abs();
    }

    private static float distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return (float) (earthRadius * c);
    }
}
