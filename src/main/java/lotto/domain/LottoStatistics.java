package lotto.domain;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public class LottoStatistics {

    private final Map<LottoRank, Integer> rankCounts;
    private final double rateOfReturn;

    public LottoStatistics(Map<LottoRank, Integer> rankCounts, double rateOfReturn) {
        this.rankCounts = Objects.requireNonNull(rankCounts);
        this.rateOfReturn = rateOfReturn;
    }

    public Map<LottoRank, Integer> getRankCounts() {
        return Collections.unmodifiableMap(rankCounts);
    }

    public double getRateOfReturn() {
        return rateOfReturn;
    }
}
