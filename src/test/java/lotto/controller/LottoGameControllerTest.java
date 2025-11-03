package lotto.controller;

import camp.nextstep.edu.missionutils.Console;
import lotto.service.LottoMachine;
import lotto.service.LottoResultCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LottoGameControllerTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;

    private final LottoMachine lottoMachine = new LottoMachine();
    private final LottoResultCalculator resultCalculator = new LottoResultCalculator();


    @BeforeEach
    void setUp() {
        // 테스트 시작 전, System.out을 캡처 스트림으로 변경
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        // 테스트 종료 후, System.out과 System.in을 원래 상태로 복원
        System.setOut(standardOut);
        System.setIn(standardIn);
        Console.close();
    }

    private void provideInput(List<String> inputs) {
        String combinedInput = String.join("\n", inputs);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(combinedInput.getBytes());
        System.setIn(inputStream);
    }

    @Test
    @DisplayName("구입 금액 입력 예외 발생 시 재입력을 요청한다.")
    void r구입금액_입력_예외_시_재입력() {
        // given: 잘못된 금액(1500) 정상 금액(2000)
        List<String> inputs = List.of("1500", "2000", "1,2,3,4,5,6", "7");
        provideInput(inputs);

        LottoGameController controller = new LottoGameController(lottoMachine, resultCalculator);

        // when
        controller.run();
        String actualOutput = outputStreamCaptor.toString();

        // then
        assertThat(actualOutput)
                .contains(
                        "[ERROR] 구입 금액은 1,000원 단위로 입력해야 합니다.",
                        "2개를 구매했습니다."
                );
        assertThat(actualOutput.split("구입금액을 입력해 주세요.")).hasSize(3);
    }

    @Test
    @DisplayName("당첨 번호 입력 예외 발생 시 재입력을 요청한다.")
    void 당첨번호_입력_예외_시_재입력() {
        // given: 정상 금액(1000) -> 잘못된 당첨 번호(중복: 1,2,3,4,5,5) -> 정상 당첨 번호(1,2,3,4,5,6)
        List<String> inputs = List.of("1000", "1,2,3,4,5,5", "1,2,3,4,5,6", "7");
        provideInput(inputs);

        LottoGameController controller = new LottoGameController(lottoMachine, resultCalculator);

        // when
        controller.run();
        String actualOutput = outputStreamCaptor.toString();

        // then
        assertThat(actualOutput)
                .contains(
                        "[ERROR] 로또 번호는 중복될 수 없습니다.",
                        "당첨 통계"
                );
        assertThat(actualOutput.split("당첨 번호를 입력해 주세요.")).hasSize(3);
    }

    @Test
    @DisplayName("보너스 번호 입력 예외 발생 시 재입력을 요청한다.")
    void 보너스번호_입력_예외_시_재입력() {
        // given: 정상 금액(1000) -> 정상 당첨 번호(1,2,3,4,5,6) -> 잘못된 보너스(중복: 5) -> 정상 보너스(7)
        List<String> inputs = List.of("1000", "1,2,3,4,5,6", "5", "7");
        provideInput(inputs);

        LottoGameController controller = new LottoGameController(lottoMachine, resultCalculator);

        // when
        controller.run();
        String actualOutput = outputStreamCaptor.toString();

        // then
        assertThat(actualOutput)
                .contains(
                        "[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.",
                        "총 수익률은"
                );
        assertThat(actualOutput.split("보너스 번호를 입력해 주세요.")).hasSize(3);
    }
}
