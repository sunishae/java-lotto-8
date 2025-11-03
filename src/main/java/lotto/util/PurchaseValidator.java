package lotto.util;

import lotto.service.LottoMachine;

public class PurchaseValidator {

    private PurchaseValidator() {
        // 인스턴스화 방지
    }

    public static int validateAndParse(String input) {
        try {
            int amount = Integer.parseInt(input.trim());
            validateUnit(amount);
            return amount;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LottoErrorMessage.NOT_A_NUMBER);
        }
    }

    private static void validateUnit(int amount) {
        validatePositive(amount);
        validateDivisibleByPrice(amount);
    }

    private static void validatePositive(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException(LottoErrorMessage.NEGATIVE_AMOUNT);
        }
    }

    private static void validateDivisibleByPrice(int amount) {
        if (amount % LottoMachine.LOTTO_PRICE != 0) {
            throw new IllegalArgumentException(LottoErrorMessage.INVALID_UNIT);
        }
    }
}
