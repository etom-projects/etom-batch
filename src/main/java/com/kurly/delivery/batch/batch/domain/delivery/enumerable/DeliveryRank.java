package com.kurly.delivery.batch.batch.domain.delivery.enumerable;

public enum DeliveryRank {
    A,
    B,
    C;

    public static DeliveryRank FreshnessToDeliveryRank(Integer freshness) {
        if (freshness >= 0 && freshness <= 3) {
            return DeliveryRank.C;
        } else if (freshness >= 4 && freshness <= 6) {
            return DeliveryRank.B;
        } else {
            return DeliveryRank.A;
        }
    }
}
