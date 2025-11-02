package lotto.domain;

import java.util.Arrays;

public enum LottoRank {
    FIRST(6, false, 2_000_000_000L, "6개 일치 (2,000,000,000원)"),
    SECOND(5, true, 30_000_000L, "5개 일치, 보너스 볼 일치 (30,000,000원)"),
    THIRD(5, false, 1_500_000L, "5개 일치 (1,500,000원)"),
    FOURTH(4, false, 50_000L, "4개 일치 (50,000원)"),
    FIFTH(3, false, 5_000L, "3개 일치 (5,000원)"),
    MISS(0, false, 0L, "꽝");

    private static final int MIN_MATCH_COUNT_FOR_PRIZE = 3;

    private final int matchCount;
    private final boolean matchBonus;
    private final long prize;
    private final String message;

    LottoRank(int matchCount, boolean matchBonus, long prize, String message) {
        this.matchCount = matchCount;
        this.matchBonus = matchBonus;
        this.prize = prize;
        this.message = message;
    }

    // 일치 개수와 보너스 볼 일치 여부에 따라 등수를 반환
    public static LottoRank valueOf(int matchCount, boolean matchBonus) {
        if (matchCount < MIN_MATCH_COUNT_FOR_PRIZE) {
            return MISS;
        }

        return Arrays.stream(values())
                .filter(rank -> rank.matchCount == matchCount)
                .filter(rank -> rank.isMatchBonus(matchBonus))
                .findFirst()
                .orElse(MISS);
    }

    // 4등, 5등은 보너스 볼 일치 여부와 관계없이 오직 matchCount만으로 결정됨
    private boolean isMatchBonus(boolean matchBonus) {
        if (this == FOURTH || this == FIFTH || this == MISS) {
            return true;
        }
        return this.matchBonus == matchBonus;
    }

    public long getPrize() {
        return prize;
    }

    public String getMessage() {
        return message;
    }
}
