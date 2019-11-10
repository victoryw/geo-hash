package geo.hash;

import lombok.NonNull;

import java.math.BigDecimal;

public class LayerBisection {
    private final int layers;
    private final double rangeBegin;
    private final double rangeEnd;

    public LayerBisection(final int layers,
                          final double rangeBegin,
                          final double rangeEnd) {

        this.layers = layers;
        this.rangeBegin = rangeBegin;
        this.rangeEnd = rangeEnd;
    }

    @NonNull
    public long split(final BigDecimal value) {
       return split(value, rangeBegin, rangeEnd, 0, 1);
    }

    @NonNull
    private long split(final BigDecimal value, final double rangeBegin,
                       final double rangeEnd, final long currentHash,
                       final int currentLayer) {
        if(currentLayer > layers) {
            return currentHash;
        }


        double mid = (rangeBegin + rangeEnd)/2;

        final var compared = value.compareTo(BigDecimal.valueOf(mid));
        if(compared == 0) {
            final var currentLayerValue = currentHash << 1 | 1;
            return currentLayerValue << (layers - currentLayer);
        }

        final int nextLayer = currentLayer + 1;
        if(compared == 1) {
            return split(value, mid, rangeEnd, currentHash << 1 | 1, nextLayer);
        }

        return split(value, rangeBegin, mid, (currentHash << 1), nextLayer);
    }

}
