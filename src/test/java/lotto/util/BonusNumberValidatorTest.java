package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static lotto.domain.Lotto.LOTTO_MAX_NUMBER;
import static lotto.domain.Lotto.LOTTO_MIN_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BonusNumberValidatorTest {

    private final List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

    @Test
    @DisplayName("유효한 보너스 번호 입력 시 정수형으로 정확히 변환한다.")
    void 우효한_보너스_번호_정상_변환() {
        String input = "7";
        assertThat(BonusNumberValidator.validateAndParse(input, winningNumbers)).isEqualTo(7);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "7a", ""})
    @DisplayName("보너스 번호에 숫자가 아닌 문자가 포함되거나 공백이면 예외가 발생한다.")
    void 보너스_번호가_숫자가_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> BonusNumberValidator.validateAndParse(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.NOT_A_NUMBER);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 46})
    @DisplayName("보너스 번호가 로또 범위(1~45)를 벗어나면 예외가 발생한다.")
    void 보너스_번호가_범위를_벗어나면_예외_발생(int invalidNumber) {
        String input = String.valueOf(invalidNumber);

        assertThatThrownBy(() -> BonusNumberValidator.validateAndParse(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_RANGE, LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER));
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    void 보너스_번호가_당첨_번호와_중복되면_예외_발생() {
        String input = "5"; // 5는 당첨 번호(1~6)에 포함됨

        assertThatThrownBy(() -> BonusNumberValidator.validateAndParse(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.BONUS_NUMBER_DUPLICATE);
    }
}
