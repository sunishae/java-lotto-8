package lotto.domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lotto.util.LottoErrorMessage;

public class Lotto {

    public static final int LOTTO_NUMBER_SIZE = 6;
    public static final int LOTTO_MIN_NUMBER = 1;
    public static final int LOTTO_MAX_NUMBER = 45;

    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validateSize(numbers);
        validateDuplicate(numbers);
        validateRange(numbers);
        this.numbers = numbers;
    }

    private void validateSize(List<Integer> numbers) {
        if (numbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException(
                    String.format(LottoErrorMessage.INVALID_SIZE, LOTTO_NUMBER_SIZE)
            );
        }
    }

    private void validateDuplicate(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);

        if (uniqueNumbers.size() != LOTTO_NUMBER_SIZE) {
            throw new IllegalArgumentException(LottoErrorMessage.DUPLICATE_NUMBER);
        }
    }

    private void validateRange(List<Integer> numbers) {
        for (int number : numbers) {
            if (number < LOTTO_MIN_NUMBER || number > LOTTO_MAX_NUMBER) {
                throw new IllegalArgumentException(
                        String.format(LottoErrorMessage.INVALID_RANGE, LOTTO_MIN_NUMBER, LOTTO_MAX_NUMBER)
                );
            }
        }
    }

    // 다른 로또와 일치하는 번호의 개수 반환
    public int getMatchCount(Lotto winningLotto) {
        int matchCount = 0;

        for (int number : numbers) {
            if (winningLotto.contains(number)) {
                matchCount++;
            }
        }
        return matchCount;
    }

    // 특정 번호가 현재 로또에 포함되어 있는지 확인
    public boolean contains(int number) {
        return numbers.contains(number);
    }

    public List<Integer> getNumbers() {
        return Collections.unmodifiableList(numbers);
    }
}
