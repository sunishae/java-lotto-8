package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoStatistics;
import lotto.domain.WinningLotto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class LottoResultCalculator {

    private static final int DECIMAL_SCALE = 1;
    private static final BigDecimal PERCENTAGE_MULTIPLIER = new BigDecimal(100);

    /**
     * 구매한 로또와 당첨 로또를 비교하여 통계 결과를 계산합니다.
     * @param purchasedLottos 구매한 로또 리스트
     * @param winningLotto 당첨 로또 정보
     * @param totalPurchaseAmount 총 구매 금액
     * @return LottoStatistics 통계 (당첨 횟수, 수익률)
     */
    public LottoStatistics calculate(List<Lotto> purchasedLottos, WinningLotto winningLotto, int totalPurchaseAmount) {
        Map<LottoRank, Integer> rankCounts = initializeRankCounts();
        long totalPrize = 0L;

        for (Lotto userLotto : purchasedLottos) {
            LottoRank rank = winningLotto.match(userLotto);
            rankCounts.put(rank, rankCounts.getOrDefault(rank, 0) + 1);
            totalPrize += rank.getPrize();
        }

        double rateOfReturn = calculateRateOfReturn(totalPrize, totalPurchaseAmount);

        return new LottoStatistics(rankCounts, rateOfReturn);
    }

    private Map<LottoRank, Integer> initializeRankCounts() {
        Map<LottoRank, Integer> rankCounts = new EnumMap<>(LottoRank.class);
        Arrays.stream(LottoRank.values()).forEach(rank -> rankCounts.put(rank, 0));
        return rankCounts;
    }

    private double calculateRateOfReturn(long totalPrize, int totalPurchaseAmount) {
        if (totalPurchaseAmount == 0) {
            return 0.0;
        }

        BigDecimal totalPrizeBd = new BigDecimal(totalPrize);
        BigDecimal totalAmountBd = new BigDecimal(totalPurchaseAmount);

        // 수익률 = (총상금 / 총구매액) * 100
        BigDecimal rate = totalPrizeBd
                .divide(totalAmountBd, 10, RoundingMode.HALF_UP)
                .multiply(PERCENTAGE_MULTIPLIER);

        // 소수점 둘째 자리에서 반올림하여 첫째 자리까지 표시
        return rate.setScale(DECIMAL_SCALE, RoundingMode.HALF_UP).doubleValue();
    }
}
