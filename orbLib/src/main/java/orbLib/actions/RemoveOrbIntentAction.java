package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class RemoveOrbIntentAction extends AbstractGameAction {

	public AbstractMonster monster;
	public OrbIntent orbIntent;
	
	public RemoveOrbIntentAction(AbstractMonster monster, OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.monster = monster;
		this.orbIntent = intent;
	}

	public void update() {
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(monster)) {
			if(intent.intent.equals(orbIntent.intent)) {
				intent.amount -= orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				if(intent.amount <= 0) {
					OrbIntentsPatch.orbIntents.get(monster).remove(intent);
				}
				break;
			}
		}

		this.isDone = true;
	}
}
