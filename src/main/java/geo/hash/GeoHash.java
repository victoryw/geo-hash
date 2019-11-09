package geo.hash;

import java.util.HashMap;

public class GeoHash {
    private static final int BITS_PER_LAYER = 5;

    private final int layers;
    private final Wgs84Point wgs84Point;
    private final LayerBisection latBisection;
    private final LayerBisection longBisection;

    public GeoHash(int layers, Wgs84Point wgs84Point) {
        this.layers = layers;
        this.wgs84Point = wgs84Point;

        final var totalBits = getTotalBits();
        this.latBisection = new LayerBisection(getLatBitNumber(),
                -90, 90);
        this.longBisection = new LayerBisection(getLongBitNum(), -180, 180);
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
        final long latBits = latBisection.split(wgs84Point.getLat());
        final long longBits = longBisection.split(wgs84Point.getLng());
        long resultCode = 0b0;
        for (int bitPos = 0; bitPos < getTotalBits(); bitPos = bitPos + 1) {
            final int valueRightPos = (getTotalBits() - bitPos) - 1;
            if (bitPos / 2 * 2 == bitPos) {
                //even
                int longPos = getLongBitNum() - bitPos / 2;
                long longBitAtPos = (longBits >> (longPos - 1)) & 1;
                resultCode = resultCode | longBitAtPos << valueRightPos;
            } else {
                //odd
                int latPos = getLatBitNumber() - (bitPos - 1) / 2;
                long latBitAtPos = (latBits >> (latPos - 1)) & 1;
                resultCode = resultCode | latBitAtPos << valueRightPos;
            }

        }

        return Base32.encodeBase32(resultCode);
    }
}

