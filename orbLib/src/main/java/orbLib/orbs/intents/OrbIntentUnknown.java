package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentUnknown extends OrbIntent {

	public OrbIntentUnknown(AbstractCreature target, int amount) {
		super(target, amount, Intents.UNKNOWN);
	}

	@Override
	public String GetIntentResource() {
		return "unknown_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentUnknown(this.target, this.amount);
	}

}
