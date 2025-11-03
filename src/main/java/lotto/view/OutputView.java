package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoStatistics;
import lotto.util.LottoOutputMessage;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class OutputView {

    private OutputView() {
        // 인스턴스화 방지
    }

    public static void printLottos(int count, List<Lotto> lottos) {
        System.out.println(String.format(LottoOutputMessage.PURCHASED_COUNT_MESSAGE, count));
        lottos.stream()
                .map(OutputView::formatLottoNumbers)
                .forEach(System.out::println);
    }

    private static String formatLottoNumbers(Lotto lotto) {
        // List<Integer>를 [n1, n2, n3, n4, n5, n6] 형태로 포맷팅
        List<Integer> numbers = lotto.getNumbers();
        String formattedNumbers = numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(LottoOutputMessage.LOTTO_NUMBER_DELIMITER));
        return "[" + formattedNumbers + "]";
    }

    public static void printStatistics(LottoStatistics statistics) {
        System.out.println(LottoOutputMessage.WINNING_STATISTICS_HEADER);
        printRankCounts(statistics.getRankCounts());
        printRateOfReturn(statistics.getRateOfReturn());
    }

    private static void printRankCounts(Map<LottoRank, Integer> rankCounts) {
        // 5등, 4등, 3등, 2등, 1등 순서로 출력하기 위해 Enum 역순으로 순회
        List<LottoRank> ranks = Arrays.asList(LottoRank.values());
        Collections.reverse(ranks);

        ranks.stream()
                .filter(rank -> rank != LottoRank.MISS)
                .forEach(rank -> {
                    int count = rankCounts.getOrDefault(rank, 0);
                    System.out.println(String.format(LottoOutputMessage.RANK_COUNT_FORMAT, rank.getMessage(), count));
                });
    }

    private static void printRateOfReturn(double rateOfReturn) {
        DecimalFormat df = new DecimalFormat("###,##0.0");
        String formattedRate = df.format(rateOfReturn);

        System.out.println(String.format(LottoOutputMessage.RATE_OF_RETURN_MESSAGE, formattedRate));
    }
}
