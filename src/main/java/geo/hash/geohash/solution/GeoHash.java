package geo.hash.geohash.solution;

import com.google.common.collect.ImmutableList;
import geo.hash.util.Base32;

import java.util.List;

public class GeoHash {
    private static final int BITS_PER_LAYER = 5;
    public static final int MAX_LAYERS = 12;
    private final int layers;
    private final Wgs84Point wgs84Point;
    private final long resultCode;
    private final long latBits;
    private final long longBits;

    public GeoHash(int layers, Wgs84Point wgs84Point) {
        this.layers = layers;
        this.wgs84Point = wgs84Point;
        latBits = new LayerBisection(getLatBitNumber(), -90, 90)
                .split(this.getWgs84Point().getLat());

        longBits = new LayerBisection(getLongBitNum(), -180, 180)
                .split(this.getWgs84Point().getLng());

        this.resultCode = merge(latBits, longBits);
    }

    private long merge(long latBits, long longBits) {
        long result = 0b0;
        for (int bitPos = 0; bitPos < getTotalBits(); bitPos = bitPos + 1) {
            final int valueRightPos = (getTotalBits() - bitPos) - 1;
            if (bitPos / 2 * 2 == bitPos) {
                //even
                int longPos = getLongBitNum() - bitPos / 2;
                long longBitAtPos = (longBits >> (longPos - 1)) & 1;
                result = result | longBitAtPos << valueRightPos;
            } else {
                //odd
                int latPos = getLatBitNumber() - (bitPos - 1) / 2;
                long latBitAtPos = (latBits >> (latPos - 1)) & 1;
                result = result | latBitAtPos << valueRightPos;
            }

        }
        return result;
    }

    private int getLongBitNum() {
        int totalBits = getTotalBits();
        return totalBits / 2 + (totalBits - totalBits / 2 * 2);
    }

    private int getLatBitNumber() {
        return getTotalBits() / 2;
    }

    private int getTotalBits() {
        return this.layers * BITS_PER_LAYER;
    }

    public String value() {
        return Base32.encodeBase32(resultCode);
    }

    //North->NorthEast->East->SouthEast->South->SouthWest->West->NorthWest
    public List<String> getNeighborGeoHashValues() {
        final var latNorthBits = this.latBits + 1;
        final var longEastBits = this.longBits + 1;
        final var latSouthBits = this.latBits - 1;
        final var longWestBits = this.longBits - 1;

        long northMergedBits = merge(latNorthBits, this.longBits);
        long northEastMergedBits = merge(latNorthBits, longEastBits);
        long eastMergedBits = merge((this.latBits), longEastBits);
        long southEastMergedBits = merge(latSouthBits, longEastBits);
        long southMergedBits = merge(latSouthBits, this.longBits);
        long southWestMergedBits = merge(latSouthBits, longWestBits);
        long westMergedBits = merge(this.latBits, longWestBits);
        long northWestMergedBits = merge(latNorthBits, longWestBits);

        return ImmutableList.of(Base32.encodeBase32(northMergedBits),
                Base32.encodeBase32(northEastMergedBits),
                Base32.encodeBase32(eastMergedBits),
                Base32.encodeBase32(southEastMergedBits),
                Base32.encodeBase32(southMergedBits),
                Base32.encodeBase32(southWestMergedBits),
                Base32.encodeBase32(westMergedBits),
                Base32.encodeBase32(northWestMergedBits));
    }

    public Wgs84Point getWgs84Point() {
        return wgs84Point;
    }
}

