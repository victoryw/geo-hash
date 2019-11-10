package geo.hash;

import geo.hash.geohash.solution.Wgs84Point;

import java.util.List;

public interface PointRepo {
    List<Wgs84Point> getByGeoHash(String geoHash, List<String> neighborGeoHashValues);
}
