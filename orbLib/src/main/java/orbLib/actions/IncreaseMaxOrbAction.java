package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.util.OrbLibUtils;

public class IncreaseMaxOrbAction extends AbstractGameAction {
	
	  public IncreaseMaxOrbAction(int slotIncrease) {
	    this.duration = Settings.ACTION_DUR_FAST;
	    this.amount = slotIncrease;
	    this.actionType = AbstractGameAction.ActionType.BLOCK;
	  }
	  
	  public void update() {
	    if (this.duration == Settings.ACTION_DUR_FAST)
	      for (int i = 0; i < this.amount; i++)
	        OrbLibUtils.increaseMaxOrbSlotsCapless(1, true);  
	    tickDuration();
	  }
}
