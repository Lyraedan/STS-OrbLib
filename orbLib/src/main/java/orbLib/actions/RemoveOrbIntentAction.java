package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class RemoveOrbIntentAction extends AbstractGameAction {

	public AbstractCreature creature;
	public OrbIntent orbIntent;
	
	public RemoveOrbIntentAction(AbstractCreature creature, OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.creature = creature;
		this.orbIntent = intent;
	}

	public void update() {
		if(creature == null) {
			this.isDone = true;
			return;
		}
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(creature)) {
			if(intent.intent.equals(orbIntent.intent)) {
				intent.amount -= orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				if(intent.amount <= 0) {
					OrbIntentsPatch.orbIntents.get(creature).remove(intent);
				}
				break;
			}
		}

		this.isDone = true;
	}
}
