package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String PURCHASE_AMOUNT_PROMPT = "구입금액을 입력해 주세요.";
    private static final String WINNING_NUMBERS_PROMPT = "\n당첨 번호를 입력해 주세요.";
    private static final String BONUS_NUMBER_PROMPT = "\n보너스 번호를 입력해 주세요.";

    private InputView() {
        // 인스턴스화 방지
    }

    public static String readPurchaseAmount() {
        System.out.println(PURCHASE_AMOUNT_PROMPT);
        return Console.readLine();
    }

    public static String readWinningNumbers() {
        System.out.println(WINNING_NUMBERS_PROMPT);
        return Console.readLine();
    }

    public static String readBonusNumber() {
        System.out.println(BONUS_NUMBER_PROMPT);
        return Console.readLine();
    }
}
