package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.OrbLib;
import orbLib.orbs.ExtendedOrb;
import orbLib.util.OrbAction;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbLibUtils;

public class RemoveOrbListenerAction extends AbstractGameAction {

	public String className;
	
	public RemoveOrbListenerAction(String className) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.className = className;
	}

	@Override
	public void update() {
		if (!OrbLib.orbListener.queue.containsKey(this.className)) {
			this.isDone = true;
			return;
		}
		System.out.println("Removing listener for " + this.className);
		OrbLib.orbListener.queue.get(this.className).clear(); // Remove any other listeners assosiated with the orb
		OrbLib.orbListener.queue.remove(this.className); // Remove the orb listener
		this.isDone = true;
	}

}
