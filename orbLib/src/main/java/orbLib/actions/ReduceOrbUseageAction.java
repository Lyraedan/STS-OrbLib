package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.orbs.ExtendedOrb;

public class ReduceOrbUseageAction extends AbstractGameAction {
	
	private AbstractOrb orb;
	
	public ReduceOrbUseageAction(AbstractOrb orb) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orb = orb;
		
	}
	
	public ReduceOrbUseageAction(AbstractOrb orb, int amt) {
		amount = amt;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orb = orb;
	}
	
	
	@Override
	public void update() {
		if(!(orb instanceof ExtendedOrb)) {
			isDone = true;
			return;
		}
		ExtendedOrb orb = (ExtendedOrb)this.orb;
		if(orb.loseEffectOverTime) {
			orb.reduceEffectiveness(amount);
			if(orb.effectAmount <= 0) {
				orb.remove();
			}
		}
		isDone = true;
	}

}
