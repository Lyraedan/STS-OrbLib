package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.orbs.intents.OrbIntent;
import orbLib.patches.OrbIntentsPatch;

public class RemoveOrbIntentAllEnemies extends AbstractGameAction {

	public OrbIntent orbIntent;
	
	public RemoveOrbIntentAllEnemies(OrbIntent intent) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orbIntent = intent;
	}

	public void update() {
		int temp = (AbstractDungeon.getCurrRoom()).monsters.monsters.size();
		for(int i = 0; i < temp; i++) {
			AbstractMonster monster = (AbstractDungeon.getCurrRoom()).monsters.monsters.get(i);
			removeIntentFrom(monster, orbIntent);
		}	
		this.isDone = true;
	}
	
	void removeIntentFrom(AbstractMonster monster, OrbIntent orbIntent) {
		System.out.println("Trying to remove " + orbIntent.intent.toString());
		for(OrbIntent intent : OrbIntentsPatch.orbIntents.get(monster)) {
			System.out.println("Does intent match: " + intent.intent.equals(orbIntent.intent));
			if(intent.intent.equals(orbIntent.intent)) {
				System.out.println("Remove intent " + orbIntent.intent.toString());
				intent.amount -= orbIntent.amount;
				intent.ReloadImage(); // Reload the image
				if(intent.amount <= 0) {
					System.out.println("Removed intent " + orbIntent.intent.toString());
					OrbIntentsPatch.orbIntents.get(monster).remove(intent);
				}
				break;
			}
		}
	}
}
