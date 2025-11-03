package lotto.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PurchaseValidatorTest {

    @Test
    @DisplayName("유효한 금액 입력 시 정수형으로 정확히 변환하여 반환한다.")
    void 유효한_금액_입력시_정수형으로_정확히_변환() {
        String input = "14000";
        assertThat(PurchaseValidator.validateAndParse(input)).isEqualTo(14000);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1500", "20001", "1"})
    @DisplayName("구입 금액이 1000원 단위가 아니면 예외가 발생한다.")
    void 구매_금액이_1000원_단위가_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> PurchaseValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.INVALID_UNIT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1000", "0"})
    @DisplayName("구입 금액이 0 이하의 값이면 예외가 발생한다.")
    void 구매_금액이_음수면_예외_발생(String input) {
        assertThatThrownBy(() -> PurchaseValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.NEGATIVE_AMOUNT);
    }

    @ParameterizedTest
    @ValueSource(strings = {"abc", "1000a", ""})
    @DisplayName("구입 금액에 숫자가 아닌 문자가 포함되거나 공백이면 예외가 발생한다.")
    void 구매_금액이_숫자형식이_아니면_예외_발생(String input) {
        assertThatThrownBy(() -> PurchaseValidator.validateAndParse(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(LottoErrorMessage.NOT_A_NUMBER);
    }
}
