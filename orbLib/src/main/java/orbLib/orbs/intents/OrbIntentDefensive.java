package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentDefensive extends OrbIntent {

	public OrbIntentDefensive(AbstractCreature target, int amount) {
		super(target, amount, Intents.DEFENSIVE);
	}

	@Override
	public String GetIntentResource() {
		return "defend_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentDefensive(this.target, this.amount);
	}

}
