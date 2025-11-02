package lotto.service;

import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoMachineTest {

    @Test
    @DisplayName("구입 금액에 맞춰 정확한 수량의 로또를 발행한다.")
    void 구입_금액에_따라_정확한_수량을_발행한다() {
        // given
        LottoMachine lottoMachine = new LottoMachine();
        int purchaseAmount = 8000;
        int expectedCount = 8; // 8000원 / 1000원 = 8장

        // when
        List<Lotto> purchasedLottos = lottoMachine.issueLottos(purchaseAmount);

        // then
        assertThat(purchasedLottos).hasSize(expectedCount);
    }

    @Test
    @DisplayName("발행된 로또 번호는 항상 오름차순으로 정렬되어 있다.")
    void 발행된_로또의_번호는_오름차순으로_정렬되어_있다() {
        // given
        LottoMachine lottoMachine = new LottoMachine();
        int purchaseAmount = 1000;

        // when
        List<Lotto> purchasedLottos = lottoMachine.issueLottos(purchaseAmount);

        // then
        Lotto lotto = purchasedLottos.get(0);
        List<Integer> numbers = lotto.getNumbers();
        assertThat(numbers).isSorted();
    }
}