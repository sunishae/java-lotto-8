package lotto.controller;

import lotto.domain.Lotto;
import lotto.domain.WinningLotto;
import lotto.service.LottoMachine;
import lotto.service.LottoResultCalculator;
import lotto.util.BonusNumberValidator;
import lotto.util.PurchaseValidator;
import lotto.util.WinningNumberValidator;
import lotto.view.InputView;
import lotto.view.OutputView;

import java.util.List;

public class LottoGameController {

    private final LottoMachine lottoMachine;
    private final LottoResultCalculator resultCalculator;

    public LottoGameController(LottoMachine lottoMachine, LottoResultCalculator resultCalculator) {
        this.lottoMachine = lottoMachine;
        this.resultCalculator = resultCalculator;
    }

    public void run() {
        // 1. 사용자 입력
        int purchaseAmount = getValidatedPurchaseAmount();

        // 2. 로또 발급 및 출력
        List<Lotto> purchasedLottos = lottoMachine.issueLottos(purchaseAmount);
        OutputView.printLottos(purchasedLottos.size(), purchasedLottos);

        // 3. 당첨 번호 및 보너스 번호 입력
        List<Integer> winningNumbers = getValidatedWinningNumbers();
        int bonusNumber = getValidatedBonusNumber(winningNumbers);

        // 4. 우승자 판별 및 출력
        WinningLotto winningLotto = new WinningLotto(new Lotto(winningNumbers), bonusNumber);

        OutputView.printStatistics(
                resultCalculator.calculate(purchasedLottos, winningLotto, purchaseAmount)
        );
    }

    private int getValidatedPurchaseAmount() {
        while (true) {
            try {
                String input = InputView.readPurchaseAmount();
                return PurchaseValidator.validateAndParse(input);
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
    }

    private List<Integer> getValidatedWinningNumbers() {
        while (true) {
            try {
                String input = InputView.readWinningNumbers();
                return WinningNumberValidator.validateAndParse(input);
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
    }

    private int getValidatedBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                String input = InputView.readBonusNumber();
                return BonusNumberValidator.validateAndParse(input, winningNumbers);
            } catch (IllegalArgumentException e) {
                printError(e);
            }
        }
    }

    private void printError(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }
}
