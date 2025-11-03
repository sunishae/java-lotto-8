package lotto.service;

import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lotto.domain.Lotto;

public class LottoMachine {

    public static final int LOTTO_PRICE = 1000;

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
        List<Integer> randomNumbers = Randoms.pickUniqueNumbersInRange(
                Lotto.LOTTO_MIN_NUMBER,
                Lotto.LOTTO_MAX_NUMBER,
                Lotto.LOTTO_NUMBER_SIZE
        );

        List<Integer> numbers = new ArrayList<>(randomNumbers);

        Collections.sort(numbers);

        return new Lotto(numbers);
    }
}
