package lotto.domain;

import lotto.util.LottoErrorMessage;

public class WinningLotto {

    private final Lotto winningNumbers;
    private final int bonusNumber;

    public WinningLotto(Lotto winningNumbers, int bonusNumber) {
        validateBonusNumberDuplicate(winningNumbers, bonusNumber);

        this.winningNumbers = winningNumbers;
        this.bonusNumber = bonusNumber;
    }

    private void validateBonusNumberDuplicate(Lotto winningNumbers, int bonusNumber) {
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(LottoErrorMessage.BONUS_NUMBER_DUPLICATE);
        }
    }

    public LottoRank match(Lotto userLotto) {
        int matchCount = userLotto.getMatchCount(winningNumbers);
        boolean matchBonus = userLotto.contains(bonusNumber);

        return LottoRank.valueOf(matchCount, matchBonus);
    }
}
