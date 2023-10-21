package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class AddOrbIntentAllEnemiesAction extends AbstractGameAction {

	public OrbIntent orbIntent;

	public AddOrbIntentAllEnemiesAction(OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orbIntent = intent;
	}

	public void update() {
		int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
		for (int i = 0; i < temp; i++) {
			AddIntentTo((AbstractDungeon.getCurrRoom()).monsters.monsters.get(i), orbIntent);
			//AbstractDungeon.actionManager.addToBottom(new AddOrbIntentAction(monster, orbIntent));
		}
		this.isDone = true;
	}
	
	void AddIntentTo(AbstractMonster monster, OrbIntent orbIntent) {
		boolean stacked = false;
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(monster)) {
			if(intent.intent.equals(orbIntent.intent)) {
				intent.amount += orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				stacked = true;
				break;
			}
		}
		if(stacked) {
			return;
		}
		
		OrbIntent intent = orbIntent.makeCopy();
		intent.target = monster;
		OrbIntentsPatch.orbIntents.get(monster).add(intent);
	}
}
