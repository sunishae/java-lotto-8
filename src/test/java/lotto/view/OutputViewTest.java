package lotto.view;

import lotto.domain.Lotto;
import lotto.domain.LottoRank;
import lotto.domain.LottoStatistics;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class OutputViewTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        // 테스트 시작 전, System.out을 캡처 스트림으로 변경
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // 테스트 종료 후, System.out을 원래 상태로 복원
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("구매 로또 수량과 번호 목록이 요구된 형식으로 출력된다.")
    void 구매_로또의_수량과_번호_목록_출력_검증() {
        // given
        int count = 2;
        List<Lotto> lottos = List.of(
                new Lotto(List.of(1, 2, 3, 4, 5, 6)),
                new Lotto(List.of(10, 20, 30, 40, 44, 45))
        );

        // when
        OutputView.printLottos(count, lottos);
        String actualOutput = outputStreamCaptor.toString().trim();

        // then
        String expectedOutput = "2개를 구매했습니다.\n[1, 2, 3, 4, 5, 6]\n[10, 20, 30, 40, 44, 45]";
        assertThat(actualOutput).isEqualTo(expectedOutput);
    }

    @Test
    @DisplayName("당첨 통계가 순서와 형식에 맞춰 정확히 출력된다.")
    void 당첨_통계_출력_검증() {
        // given
        Map<LottoRank, Integer> rankCounts = Map.of(
                LottoRank.FIRST, 1,
                LottoRank.THIRD, 1,
                LottoRank.FIFTH, 2,
                LottoRank.MISS, 5
        );
        double rateOfReturn = 394500.0;
        LottoStatistics statistics = new LottoStatistics(rankCounts, rateOfReturn);

        // 기대되는 출력 순서: 5등, 4등, 3등, 2등, 1등
        String expectedOutput =
                "\n당첨 통계\n---" +
                        "\n3개 일치 (5,000원) - 2개" + // FIFTH
                        "\n4개 일치 (50,000원) - 0개" + // FOURTH (없어도 0개로 출력되어야 함)
                        "\n5개 일치 (1,500,000원) - 1개" + // THIRD
                        "\n5개 일치, 보너스 볼 일치 (30,000,000원) - 0개" + // SECOND (없어도 0개로 출력되어야 함)
                        "\n6개 일치 (2,000,000,000원) - 1개" + // FIRST
                        "\n총 수익률은 394,500.0%입니다.";

        // when
        OutputView.printStatistics(statistics);
        String actualOutput = outputStreamCaptor.toString().trim();

        // then
        assertThat(actualOutput).isEqualTo(expectedOutput.trim());
    }
}
