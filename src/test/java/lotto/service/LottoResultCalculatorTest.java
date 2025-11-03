package lotto.service;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoStatistics;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LottoResultCalculatorTest {

    private final Lotto WINNING_NUMBERS = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    private final int BONUS_NUMBER = 7;
    private final WinningLotto WINNING_LOTTO = new WinningLotto(WINNING_NUMBERS, BONUS_NUMBER);
    private final int TOTAL_PURCHASE_AMOUNT = 8000;

    // 8,000원 구매 (로또 8장) 시나리오를 위한 테스트 로또 목록
    private List<Lotto> createTestLottos() {
        return List.of(
                // 2등 (5개 일치 + 보너스) - 30,000,000원
                new Lotto(List.of(1, 2, 3, 4, 5, 7)),
                // 3등 (5개 일치) - 1,500,000원
                new Lotto(List.of(1, 2, 3, 4, 5, 8)),
                // 4등 (4개 일치) - 50,000원
                new Lotto(List.of(1, 2, 3, 4, 10, 11)),
                // 5등 (3개 일치) - 5,000원
                new Lotto(List.of(1, 2, 3, 12, 13, 14)),
                // 5등 (3개 일치) - 5,000원
                new Lotto(List.of(1, 2, 3, 15, 16, 17)),
                // 꽝 (2개 일치)
                new Lotto(List.of(1, 2, 18, 19, 20, 21)),
                // 꽝 (1개 일치)
                new Lotto(List.of(1, 22, 23, 24, 25, 26)),
                // 꽝 (0개 일치)
                new Lotto(List.of(27, 28, 29, 30, 31, 32))
        );
    }

    @Test
    @DisplayName("구매한 로또 리스트로 등수별 당첨 횟수를 정확히 계산한다.")
    void 등수별_횟수를_정확히_집계한다() {
        // given
        LottoResultCalculator calculator = new LottoResultCalculator();
        List<Lotto> purchasedLottos = createTestLottos();

        // when
        LottoStatistics statistics = calculator.calculate(purchasedLottos, WINNING_LOTTO, TOTAL_PURCHASE_AMOUNT);
        Map<LottoRank, Integer> rankCounts = statistics.getRankCounts();

        // then
        // 각 등수별 횟수 검증
        assertThat(rankCounts.get(LottoRank.SECOND)).isEqualTo(1); // 2등 1개
        assertThat(rankCounts.get(LottoRank.THIRD)).isEqualTo(1);  // 3등 1개
        assertThat(rankCounts.get(LottoRank.FOURTH)).isEqualTo(1); // 4등 1개
        assertThat(rankCounts.get(LottoRank.FIFTH)).isEqualTo(2);  // 5등 2개

        // 꽝(MISS) 개수 확인: 총 8장 - (1+1+1+2) = 3장
        assertThat(rankCounts.get(LottoRank.MISS)).isEqualTo(3);
    }

    @Test
    @DisplayName("총 상금을 기반으로 수익률을 정확히 계산한다.")
    void 수익률을_정확히_계산한다() {
        // given
        LottoResultCalculator calculator = new LottoResultCalculator();
        List<Lotto> purchasedLottos = createTestLottos();

        // 총 상금: 31,560,000원
        // 총 구매 금액: 8,000원
        // 수익률: (31,560,000 / 8,000) * 100 = 394500.0%

        // when
        LottoStatistics statistics = calculator.calculate(purchasedLottos, WINNING_LOTTO, TOTAL_PURCHASE_AMOUNT);
        double rateOfReturn = statistics.getRateOfReturn();

        // then
        assertThat(rateOfReturn).isEqualTo(394500.0);
    }
}