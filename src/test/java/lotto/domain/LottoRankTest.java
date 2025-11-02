package lotto.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LottoRankTest {

    @Test
    @DisplayName("6개 번호 일치 시 1등(FIRST)을 반환한다.")
    void 매치_6개_1등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(6, false);
        assertThat(actualRank).isEqualTo(LottoRank.FIRST);
    }

    @Test
    @DisplayName("5개 번호와 보너스 번호 일치 시 2등(SECOND)을 반환한다.")
    void 매치_5개_보너스_일치_2등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(5, true);
        assertThat(actualRank).isEqualTo(LottoRank.SECOND);
    }

    @Test
    @DisplayName("5개 번호 일치 시 3등(THIRD)을 반환한다.")
    void 매치_5개_보너스_불일치_3등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(5, false);
        assertThat(actualRank).isEqualTo(LottoRank.THIRD);
    }

    @Test
    @DisplayName("4개 번호 일치 시 4등(FOURTH)을 반환한다.")
    void 매치_4개_4등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(4, false);
        assertThat(actualRank).isEqualTo(LottoRank.FOURTH);
    }

    @Test
    @DisplayName("4개 번호 일치와 보너스 일치 시에도 4등(FOURTH)을 반환한다.")
    void 매치_4개_보너스_일치_4등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(4, true);
        assertThat(actualRank).isEqualTo(LottoRank.FOURTH);
    }

    @Test
    @DisplayName("3개 번호 일치 시 5등(FIFTH)을 반환한다.")
    void 매치_3개_5등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(3, false);
        assertThat(actualRank).isEqualTo(LottoRank.FIFTH);
    }

    @Test
    @DisplayName("3개 번호 일치와 보너스 일치 시에도 5등(FIFTH)을 반환한다.")
    void 매치_3개_보너스_일치_5등을_반환한다() {
        LottoRank actualRank = LottoRank.valueOf(3, true);
        assertThat(actualRank).isEqualTo(LottoRank.FIFTH);
    }

    @Test
    @DisplayName("3개 미만 일치 시 MISS를 반환한다.")
    void 세_개_미만_일치시_MISS를_반환한다() {
        // 0개 일치
        assertThat(LottoRank.valueOf(0, false)).isEqualTo(LottoRank.MISS);
        // 2개 일치
        assertThat(LottoRank.valueOf(2, true)).isEqualTo(LottoRank.MISS);
    }
}
