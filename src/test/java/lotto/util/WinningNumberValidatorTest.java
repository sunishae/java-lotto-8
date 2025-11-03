package lotto.util;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static lotto.domain.Lotto.LOTTO_MAX_NUMBER;
import static lotto.domain.Lotto.LOTTO_MIN_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningNumberValidatorTest {

    @Test
    @DisplayName("유효한 당첨 번호 입력 시 List<Integer>로 정확히 변환한다.")
    void validateAndParse_정상_변환() {
        String input = "1,2,3,4,5,6";
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);

        assertThat(WinningNumberValidator.validateAndParse(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5", "1,2,3,4,5,6,7"})
    @DisplayName("당첨 번호 개수가 6개가 아니면 예외가 발생한다.")
    void validateAndParse_개수_예외(String input) {
        assertThatThrownBy(() -> WinningNumberValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_SIZE, Lotto.LOTTO_NUMBER_SIZE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,5", "10,10,20,30,40,45"})
    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    void validateAndParse_중복_예외(String input) {
        assertThatThrownBy(() -> WinningNumberValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.DUPLICATE_NUMBER);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,0", "1,2,3,4,5,46"})
    @DisplayName("당첨 번호가 로또 범위(1~45)를 벗어나면 예외가 발생한다.")
    void validateAndParse_범위_예외(String input) {
        assertThatThrownBy(() -> WinningNumberValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(String.format(LottoErrorMessage.INVALID_RANGE, LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER));
    }

    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,a", "1,2,3,,4,5,6", "1,2,3,4,5, "})
    @DisplayName("당첨 번호에 숫자가 아닌 문자가 포함되거나 파싱할 수 없으면 예외가 발생한다.")
    void validateAndParse_파싱_예외(String input) {
        assertThatThrownBy(() -> WinningNumberValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.NOT_A_NUMBER);
    }
}
