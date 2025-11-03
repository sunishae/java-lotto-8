package lotto.util;

public class LottoErrorMessage {
    private LottoErrorMessage() {
        // 인스턴스화 방지
    }

    // 공통 에러 메시지
    public static final String NOT_A_NUMBER = "[ERROR] 숫자가 아닌 문자가 포함되어 있습니다.";

    // 로또 번호 관련 에러 메시지 (Lotto 클래스)
    public static final String INVALID_SIZE = "[ERROR] 로또 번호는 정확히 %d개여야 합니다.";
    public static final String DUPLICATE_NUMBER = "[ERROR] 로또 번호는 중복될 수 없습니다.";
    public static final String INVALID_RANGE = "[ERROR] 로또 번호는 %d부터 %d 사이의 숫자여야 합니다.";

    // 구입 금액 관련 에러 메시지 (PurchaseValidator)
    public static final String INVALID_UNIT = "[ERROR] 구입 금액은 1,000원 단위로 입력해야 합니다.";
    public static final String NEGATIVE_AMOUNT = "[ERROR] 구입 금액은 양수여야 합니다.";

    // 당첨/보너스 번호 관련 에러 메시지 (WinningNumberValidator, BonusNumberValidator)
    public static final String BONUS_NUMBER_DUPLICATE = "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.";
}
