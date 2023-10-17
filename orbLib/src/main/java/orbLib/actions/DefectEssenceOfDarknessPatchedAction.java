package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.orbs.DefectDarkOrb;

public class DefectEssenceOfDarknessPatchedAction extends AbstractGameAction {
	
	private boolean evokeAllOnFull = false;
	
	public DefectEssenceOfDarknessPatchedAction(int potency, boolean evokeAllOnFull) {
	    this.duration = Settings.ACTION_DUR_FAST;
	    this.amount = potency;
	    this.evokeAllOnFull = evokeAllOnFull;
	  }
	  
	  public void update() {
	    if (this.duration == Settings.ACTION_DUR_FAST) {
	      for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
	        for (int j = 0; j < this.amount; j++)
	        	AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectDarkOrb(), evokeAllOnFull));
	      } 
	      if (Settings.FAST_MODE) {
	        this.isDone = true;
	        return;
	      } 
	    } 
	    tickDuration();
	  }
}
