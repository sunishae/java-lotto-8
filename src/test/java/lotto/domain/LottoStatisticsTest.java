package lotto.domain;

import lotto.util.LottoErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoStatisticsTest {

    private Map<LottoRank, Integer> createSampleRankCounts() {
        Map<LottoRank, Integer> rankCounts = new HashMap<>();
        rankCounts.put(LottoRank.FIRST, 1);
        rankCounts.put(LottoRank.THIRD, 2);
        rankCounts.put(LottoRank.MISS, 5);
        return rankCounts;
    }

    @Test
    @DisplayName("생성 시 주입된 당첨 횟수와 수익률을 정확히 반환한다.")
    void 생성자와_Getter_정상_작동_검증() {
        // given
        Map<LottoRank, Integer> rankCounts = createSampleRankCounts();
        double rateOfReturn = 62.5;

        // when
        LottoStatistics statistics = new LottoStatistics(rankCounts, rateOfReturn);

        // then
        assertThat(statistics.getRankCounts()).isEqualTo(rankCounts);
        assertThat(statistics.getRateOfReturn()).isEqualTo(rateOfReturn);
    }

    @Test
    @DisplayName("getRankCounts()로 반환된 맵은 수정 불가능하다.")
    void 반환된_맵_불변성_검증() {
        // given
        LottoStatistics statistics = new LottoStatistics(createSampleRankCounts(), 100.0);
        Map<LottoRank, Integer> unmodifiableMap = statistics.getRankCounts();

        // when & then
        assertThatThrownBy(() -> unmodifiableMap.put(LottoRank.FOURTH, 1))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    @DisplayName("getRankCounts()로 반환된 맵이 원본과 동일한지 확인한다.")
    void 반환된_맵_동일성_검증() {
        // given
        Map<LottoRank, Integer> originalMap = createSampleRankCounts();
        LottoStatistics statistics = new LottoStatistics(originalMap, 100.0);

        // when
        Map<LottoRank, Integer> returnedMap = statistics.getRankCounts();

        // then
        assertThat(returnedMap).isEqualTo(originalMap);
    }
}
