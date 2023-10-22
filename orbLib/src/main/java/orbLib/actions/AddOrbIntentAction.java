package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class AddOrbIntentAction extends AbstractGameAction {

	public AbstractCreature creature;
	public OrbIntent orbIntent;
	
	public AddOrbIntentAction(AbstractCreature monster, OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.creature = monster;
		this.orbIntent = intent;
	}

	public void update() {
		if(creature == null) {
			this.isDone = true;
			return;
		}
		
		boolean stacked = false;
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(creature)) {
			if(intent.intent.equals(orbIntent.intent)) {
				intent.amount += orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				stacked = true;
				break;
			}
		}
		if(stacked) {
			this.isDone = true;
			return;
		}
		OrbIntentsPatch.orbIntents.get(creature).add(orbIntent);
		this.isDone = true;
	}
}
