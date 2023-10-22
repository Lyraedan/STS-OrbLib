package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentSleep extends OrbIntent {

	public OrbIntentSleep(AbstractCreature target, int amount) {
		super(target, amount, Intents.SLEEPING);
	}

	@Override
	public String GetIntentResource() {
		return "sleep_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentSleep(this.target, this.amount);
	}

}
