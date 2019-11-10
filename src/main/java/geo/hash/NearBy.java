package geo.hash;


import geo.hash.geohash.solution.Wgs84Point;
import geo.hash.geohash.solution.GeoHash;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class NearBy {
    private final PointRepo pointRepo;

    public NearBy(PointRepo pointRepo) {
        this.pointRepo = pointRepo;
    }

    public List<Wgs84Point> get(Wgs84Point standByPoint, int distance) {
        ///TODO: this should be get by distance
        final var detectLayerNumber = 9;
        final var standByPointHash = new GeoHash(detectLayerNumber, standByPoint);
        final var mayPoints = pointRepo.getByGeoHash(
                standByPointHash.value(),
                standByPointHash.getNeighborGeoHashValues());
        return mayPoints.stream()
                .filter(point ->
                        point.distanceTo(standByPoint).
                                compareTo(BigDecimal.valueOf(distance)) < 1)
                .collect(Collectors.toList());
    }
}
