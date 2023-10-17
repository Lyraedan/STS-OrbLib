package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.orbs.ExtendedOrb;

public class IncreaseOrbUseageAction extends AbstractGameAction {
	
	private AbstractOrb orb;
	
	public IncreaseOrbUseageAction(AbstractOrb orb) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orb = orb;
		
	}
	
	public IncreaseOrbUseageAction(AbstractOrb orb, int amt) {
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
		orb.increaseEffectiveness(amount);
		isDone = true;
	}

}
