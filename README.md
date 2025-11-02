# java-lotto-precourse

---

![Generic badge](https://img.shields.io/badge/precourse-week3-green.svg)

> 우아한테크코스 백엔드 8기 3주차, 로또를 구현한 저장소입니다.


--- 

## 기능 구현 목록(ToDo List)

---

- **LottoGameController (컨트롤러)**
    - [ ]  **run()**: 게임 전체 흐름 제어 및 입력 재시도 루프 관리
        - **구입 금액 처리**: `InputView.readPurchaseAmount()` → `PurchaseValidator.validateAndParse()`
        - **로또 발급**: `LottoMachine.issueLottos()`를 통해 `List<Lotto>` 받기.
        - **당첨 번호 처리**: `InputView.readWinningNumbers()` → `WinningNumberValidator.validateAndParse()`
        - **보너스 번호 처리**: `InputView.readBonusNumber()` → `BonusNumberValidator.validateAndParse()`
        - **결과 계산**: `LottoResultCalculator.calculate()`를 통해 `LottoStatistics` 받기.
        - **결과 출력**: `OutputView.printLottos()`, `OutputView.printStatistics()` 호출 후 종료.3


- **InputView (입력)**
    - [ ]  **readPurchaseAmount()**: "구입금액을 입력해 주세요." 출력 후 문자열 입력받아 반환.
    - [ ]  **readWinningNumbers()**: "당첨 번호를 입력해 주세요." 출력 후 문자열 입력받아 반환.
    - [ ]  **readBonusNumber()**: "보너스 번호를 입력해 주세요." 출력 후 문자열 입력받아 반환.


- **OutputView (출력)**
    - [ ]  **printLottos(int count, List<Lotto> lottos)**:
        - "N개를 구매했습니다." 출력.
        - 각 로또 번호를 오름차순으로 정렬하여 출력.
    - [ ]  **printStatistics(LottoStatistics statistics)**:
        - "당첨 통계" 및 구분선(---) 출력.
        - 각 등수별 당첨 횟수를 형식에 맞춰 출력.
        - 총 수익률을 소수점 둘째 자리에서 반올림하여 백분율로 출력.


- **PurchaseValidator (구입 금액 유효성 검사)**
    - [ ]  **validateAndParse(String input)**:
        - `input` 문자열에 대한 유효성 검사 및 정수형으로 변환 후 반환.
        - **숫자 형식 검사:** 숫자가 아닌 문자 포함 시 `IllegalArgumentException`.
        - **1000 단위 검사:** 1000으로 나누어 떨어지지 않거나 0 이하일 경우 `IllegalArgumentException`.


- **LottoMachine (로또 생성 로직)**
    - [ ]  **purchaseLottos(int purchaseAmount)**:
        - 구매 금액을 기반으로 로또 수량 계산
        - 수량만큼 `Lotto` 객체 생성 및 `List<Lotto>`로 반환.


- **Lotto (모델 - 사용자 로또)**
    - 속성: `numbers` (`List<Integer>`, 6개)
    - [x]  **Lotto(List<Integer> numbers)**: 생성자 유효성 검사 (6개, 중복, 1~45 범위).
    - [x]  **getNumbers()**: 로또 번호 리스트 반환.
    - [x]  **getMatchCount(Lotto winningLotto)**: 당첨 번호와 일치하는 개수 반환.


- **WinningLotto (모델 - 로또 당첨 번호)**
    - 속성: `winningNumbers` (`Lotto`), `bonusNumber` (`int`)
    - [ ]  **WinningLotto(Lotto winningLotto, int bonusNumber)**: 당첨 번호와 보너스 번호를 인자로 받아 초기화.
    - [ ]  **match(Lotto userLotto)**: 사용자의 로또를 받아 `LottoRank`를 반환.


- **BonusNumberValidator (보너스 번호 유효성 검사)**
    - [ ]  **validateAndParse(String input, List<Integer> winningNumbers)**:
        - `input` 문자열을 검사하고 정수형(`int`)으로 변환 후 반환.
        - **숫자 형식 및 범위 검사:** 1~45 범위를 벗어나거나 숫자가 아닌 경우 `IllegalArgumentException`.
        - **당첨 번호 중복 검사:** 이미 `winningNumbers`에 포함된 경우 `IllegalArgumentException`.


- **LottoResultCalculator (결과 계산)**
    - [ ]  **calculate(List<Lotto> purchasedLottos, WinningLotto winningLotto)**:
        - 모든 구매 로또를 `winningLotto`와 비교하여 등수별 당첨 횟수를 집계.
        - `LottoStatistics` 객체 (당첨 횟수 `Map` 및 수익률)로 변환하여 반환.


- **LottoRank (Enum - 당첨 등수)**
    - [x] Enum 정의: 등수별 일치 개수, 보너스 일치 여부, 상금, 출력 메시지 속성
    - [x] valueOf(matchCount, matchBonus): 주어진 조건에 맞는 등수(LottoRank 상수)를 반환.
    - [x] Getter: 상금(getPrize()) 및 메시지(getMessage()) 반환.


- **LottoStatistics (결과 통계)**
    - 속성: `rankCounts` (`Map<LottoRank, Integer>`), `rateOfReturn` (`double`)
    - [ ]  **LottoStatistics()**: 총 상금과 구매 금액을 받아 수익률까지 계산하여 초기화.
    - [ ]  **getRankCounts()**: 등수별 당첨 횟수 맵 반환.
    - [ ]  **getRateReturn()**: 수익률 반환.