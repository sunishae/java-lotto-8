package lotto.util;

public class LottoOutputMessage {
    private LottoOutputMessage() {
        // 인스턴스화 방지
    }

    // 로또 발행 관련 메시지
    public static final String PURCHASED_COUNT_MESSAGE = "\n%d개를 구매했습니다.";
    public static final String LOTTO_NUMBER_DELIMITER = ", ";

    // 당첨 통계 관련 메시지
    public static final String WINNING_STATISTICS_HEADER = "\n당첨 통계\n---";
    public static final String RANK_COUNT_FORMAT = "%s - %d개";
    public static final String RATE_OF_RETURN_MESSAGE = "총 수익률은 %s%%입니다.";
}
