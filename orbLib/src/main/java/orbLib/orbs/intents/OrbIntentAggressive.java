package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentAggressive extends OrbIntent {

	public OrbIntentAggressive(AbstractCreature target, int amount) {
		super(target, amount, Intents.AGGRESSIVE);
	}

	@Override
	public String GetIntentResource() {
		if(amountBetweenRanges(0, 4)) {
			return "attack_intent_1.png";
		} else if(amountBetweenRanges(5, 9)) {
			return "attack_intent_2.png";
		} else if(amountBetweenRanges(10, 14)) {
			return "attack_intent_3.png";
		} else if(amountBetweenRanges(15, 19)) {
			return "attack_intent_4.png";
		} else if(amountBetweenRanges(20, 24)) {
			return "attack_intent_5.png";
		} else if(amountBetweenRanges(25, 29)) {
			return "attack_intent_6.png";
		} else if(amount >= 30) {
			return "attack_intent_7.png";
		}
		return "attack_intent_1.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentAggressive(this.target, this.amount);
	}

}
