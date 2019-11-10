package geo.hash.fixture;

import com.google.common.collect.ImmutableList;
import geo.hash.geohash.solution.GeoHash;
import geo.hash.PointRepo;
import geo.hash.geohash.solution.Wgs84Point;

import java.util.List;
import java.util.stream.Collectors;

public class PointTestRepo implements PointRepo {

    private List<Wgs84Point> points;

    public PointTestRepo(Wgs84Point... preparePoints) {
        points = ImmutableList.copyOf(preparePoints);
    }

    @Override
    public List<Wgs84Point> getByGeoHash(String geoHash, List<String> neighborGeoHashValues) {
        return points.stream().filter(point -> {
            final var poiHashValue = new GeoHash(GeoHash.MAX_LAYERS, point).value();
            return poiHashValue.startsWith(geoHash) ||
                    neighborGeoHashValues.stream().anyMatch(poiHashValue::startsWith);
        }).collect(Collectors.toUnmodifiableList());
    }
}
