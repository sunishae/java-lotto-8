package lotto.domain;

import lotto.util.LottoErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    private final Lotto winningNumbers = new Lotto(List.of(1, 2, 3, 4, 5, 6));
    private final static int WINNING_BONUS_NUMBER = 7;

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    void 보너스_번호가_당첨_번호와_중복되면_예외_발생() {
        // 보너스 번호 5는 이미 당첨 번호(1~6)에 포함되어 있음
        int bonusNumber = 5;

        assertThatThrownBy(() -> new WinningLotto(winningNumbers, bonusNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.BONUS_NUMBER_DUPLICATE);
    }

    @Test
    @DisplayName("당첨 번호와 보너스 번호가 중복되지 않으면 객체가 정상 생성된다.")
    void 보너스_번호가_중복되지_않으면_객체_정상_생성() {
        int bonusNumber = 7;

        // 예외가 발생하지 않는지 확인
        assertThat(new WinningLotto(winningNumbers, bonusNumber)).isNotNull();
    }


    @Test
    @DisplayName("사용자 로또와 비교하여 정확한 당첨 등수를 반환한다.")
    void 사용자_로또와_비교하여_정확한_당첨_등수_반환() {
        // 당첨 로또 : 1, 2, 3, 4, 5, 6 | 보너스: 7
        WinningLotto winningLotto = new WinningLotto(winningNumbers, WINNING_BONUS_NUMBER);

        // 2등 당첨: 5개 일치 (1,2,3,4,5) + 보너스 일치 (7)
        Lotto secondLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7));
        assertThat(winningLotto.match(secondLotto)).isEqualTo(LottoRank.SECOND);

        // 3등 당첨: 5개 일치 (1,2,3,4,5) + 보너스 불일치 (8)
        Lotto thirdLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8));
        assertThat(winningLotto.match(thirdLotto)).isEqualTo(LottoRank.THIRD);

        // 4등 당첨: 4개 일치 (1,2,3,4) + 보너스 불일치
        Lotto fourthLotto = new Lotto(List.of(1, 2, 3, 4, 9, 10));
        assertThat(winningLotto.match(fourthLotto)).isEqualTo(LottoRank.FOURTH);

        // 5등 당첨: 3개 일치 (1,2,3) + 보너스 불일치
        Lotto fifthLotto = new Lotto(List.of(1, 2, 3, 9, 10, 11));
        assertThat(winningLotto.match(fifthLotto)).isEqualTo(LottoRank.FIFTH);

        // 꽝 : 2개 일치 (1,2) + 보너스 일치
        Lotto missLotto = new Lotto(List.of(1, 2, 7, 8, 9, 10));
        assertThat(winningLotto.match(missLotto)).isEqualTo(LottoRank.MISS);
    }
}