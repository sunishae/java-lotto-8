package lotto.domain;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lotto.util.LottoErrorMessage;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_아니면_예외가_발생한다() {
        // 7개인 경우
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_SIZE, Lotto.LOTTO_NUMBER_SIZE));

        // 5개인 경우
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_SIZE, Lotto.LOTTO_NUMBER_SIZE));
    }

    @Test
    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.DUPLICATE_NUMBER);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46})
    @DisplayName("로또 번호의 범위(1~45)를 벗어나면 예외가 발생한다.")
    void 로또_번호의_범위를_벗어나면_예외가_발생한다(int invalidNumber) {
        List<Integer> invalidNumbers = Stream.of(1, 2, 3, 4, 5, invalidNumber)
                .collect(Collectors.toList());

        assertThatThrownBy(() -> new Lotto(invalidNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_RANGE, Lotto.LOTTO_MIN_NUMBER, Lotto.LOTTO_MAX_NUMBER));
    }

    @Test
    @DisplayName("다른 로또와 일치하는 번호의 개수를 정확하게 반환한다.")
    void 다른_로또와_일치하는_번호의_개수를_정확하게_반환한다() {
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        // 0개 일치
        Lotto winningLottoZeroMatch = new Lotto(List.of(7, 8, 9, 10, 11, 12));
        assertThat(userLotto.getMatchCount(winningLottoZeroMatch)).isEqualTo(0);

        // 4개 일치
        Lotto winningLottoFourMatch = new Lotto(List.of(1, 2, 3, 4, 44, 45));
        assertThat(userLotto.getMatchCount(winningLottoFourMatch)).isEqualTo(4);

        // 6개 일치
        Lotto winningLottoSixMatch = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(userLotto.getMatchCount(winningLottoSixMatch)).isEqualTo(6);
    }

    @Test
    @DisplayName("로또 번호에 특정 번호가 포함되어 있는지 확인한다.")
    void 로또_번호에_특정_번호가_포함되어_있는지_확인한다() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(6)).isTrue();
    }

    @Test
    @DisplayName("로또 번호에 특정 번호가 포함되어 있지 않은지 확인한다.")
    void 로또_번호에_특정_번호가_포함되어_있지_않은지_확인한다() {
        Lotto lotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));

        assertThat(lotto.contains(7)).isFalse();
    }
}
