package orbLib.orbs.intents;

import com.megacrit.cardcrawl.core.AbstractCreature;

public class OrbIntentBuff extends OrbIntent {

	public OrbIntentBuff(AbstractCreature target, int amount) {
		super(target, amount, Intents.BUFF);
	}

	@Override
	public String GetIntentResource() {
		return "buff_intent.png";
	}

	@Override
	public OrbIntent makeCopy() {
		return new OrbIntentBuff(this.target, this.amount);
	}

}
