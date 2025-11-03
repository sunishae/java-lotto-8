package lotto;

import lotto.controller.LottoGameController;
import lotto.service.LottoMachine;
import lotto.service.LottoResultCalculator;

public class Application {
    public static void main(String[] args) {
        LottoMachine lottoMachine = new LottoMachine();
        LottoResultCalculator resultCalculator = new LottoResultCalculator();

        LottoGameController controller = new LottoGameController(
                lottoMachine,
                resultCalculator
        );

        controller.run();
    }
}
