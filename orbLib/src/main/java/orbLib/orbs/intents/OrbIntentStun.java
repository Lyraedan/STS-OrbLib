package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentStun extends OrbIntent {

	public OrbIntentStun(AbstractCreature target, int amount) {
		super(target, amount, Intents.STUNNED);
	}

	@Override
	public String GetIntentResource() {
		return "stun_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentStun(this.target, this.amount);
	}

}
