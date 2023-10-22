package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentDebuff extends OrbIntent {

	public OrbIntentDebuff(AbstractCreature target, int amount) {
		super(target, amount, Intents.DEBUFF);
	}

	@Override
	public String GetIntentResource() {
		if(this.amountBetweenRanges(0, 9)) {
			return "debuff_intent_1.png";
		} else if(this.amount >= 10) {
			return "debuff_intent_2.png";
		}
		return "unknown_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentDebuff(this.target, this.amount);
	}

}
