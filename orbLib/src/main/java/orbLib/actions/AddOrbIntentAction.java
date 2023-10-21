package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class AddOrbIntentAction extends AbstractGameAction {

	public AbstractMonster monster;
	public OrbIntent orbIntent;
	
	public AddOrbIntentAction(AbstractMonster monster, OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.monster = monster;
		this.orbIntent = intent;
	}

	public void update() {
		boolean stacked = false;
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(monster)) {
			if(intent.intent.equals(orbIntent.intent)) {
				intent.amount += orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				System.out.println("Stacking intent " + orbIntent.intent.toString());
				stacked = true;
				break;
			}
		}
		if(stacked) {
			this.isDone = true;
			return;
		}
		OrbIntentsPatch.orbIntents.get(monster).add(orbIntent);
		System.out.println("Added new intent " + orbIntent.intent.toString());
		this.isDone = true;
	}
}
