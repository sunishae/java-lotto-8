package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lotto.domain.Lotto;

public class LottoMachine {

    private static final int LOTTO_PRICE = 1000;

    public List<Lotto> issueLottos(int purchaseAmount) {
        int count = calculateLottoCount(purchaseAmount);
        List<Lotto> lottos = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            lottos.add(generateLotto());
        }

        return lottos;
    }

    private int calculateLottoCount(int purchaseAmount) {
        return purchaseAmount / LOTTO_PRICE;
    }

    private Lotto generateLotto() {
        // Lotto 클래스에 정의된 상수를 사용합니다.
        List<Integer> numbers = Randoms.pickUniqueNumbersInRange(
                Lotto.LOTTO_MIN_NUMBER,
                Lotto.LOTTO_MAX_NUMBER,
                Lotto.LOTTO_NUMBER_SIZE
        );

        Collections.sort(numbers);

        return new Lotto(numbers);
    }
}
