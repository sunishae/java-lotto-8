package lotto.util;

import lotto.domain.Lotto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WinningNumberValidator {

    private static final String NUMBER_DELIMITER = ",";

    private WinningNumberValidator() {
        // 인스턴스화 방지
    }

    public static List<Integer> validateAndParse(String input) {
        List<String> tokens = Arrays.asList(input.split(NUMBER_DELIMITER));
        List<Integer> numbers = parseToIntegers(tokens);

        // 로또 번호 관련 유효성 검사는 Lotto에 위임
        new Lotto(numbers);

        return numbers;
    }

    private static List<Integer> parseToIntegers(List<String> tokens) {
        try {
            return tokens.stream()
                    .map(String::trim)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(LottoErrorMessage.NOT_A_NUMBER);
        }
    }
}
