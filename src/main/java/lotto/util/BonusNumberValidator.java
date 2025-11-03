package lotto.util;

import lotto.domain.Lotto;
import java.util.List;

public class BonusNumberValidator {

    private BonusNumberValidator() {
        // 인스턴스화 방지
    }

    public static int validateAndParse(String input, List<Integer> winningNumbers) {
        int number = parseToInteger(input);
        validateRange(number);
        validateDuplicate(number, winningNumbers);
        return number;
    }

    private static int parseToInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LottoErrorMessage.NOT_A_NUMBER);
        }
    }

    private static void validateRange(int number) {
        if (number < Lotto.LOTTO_MIN_NUMBER || number > Lotto.LOTTO_MAX_NUMBER) {
            throw new IllegalArgumentException(
                    String.format(LottoErrorMessage.INVALID_RANGE, Lotto.LOTTO_MIN_NUMBER, Lotto.LOTTO_MAX_NUMBER)
            );
        }
    }

    private static void validateDuplicate(int bonusNumber, List<Integer> winningNumbers) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(LottoErrorMessage.BONUS_NUMBER_DUPLICATE);
        }
    }
}
