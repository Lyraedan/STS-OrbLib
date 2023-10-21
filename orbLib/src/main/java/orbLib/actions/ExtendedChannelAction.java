package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeAllOrbsAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import orbLib.OrbLib;
import orbLib.orbs.ExtendedOrb;
import orbLib.orbs.intents.OrbIntentAggressive;

public class ExtendedChannelAction extends AbstractGameAction{

	private ExtendedOrb orb;
	private boolean evokeAllWhenFull = true;
	
	public ExtendedChannelAction(ExtendedOrb orb, boolean evokeAllWhenFull) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.orb = orb;
		this.evokeAllWhenFull = evokeAllWhenFull;
	}
	
	public ExtendedChannelAction(ExtendedOrb orb) {
		this(orb, OrbLib.CONFIG_EVOKE_ALL_ORBS_ON_FULL);
	}
	
	@Override
	public void update() {		
		if(!(orb instanceof ExtendedOrb)) {
			isDone = true;
			return;
		}
		
        AbstractDungeon.actionManager.addToBottom(new ChannelAction(this.orb));

        if(evokeAllWhenFull) {
	        int slots = 0;
	        for(int i = 0; i < AbstractDungeon.player.maxOrbs; i++) {
			    if (!AbstractDungeon.player.orbs.isEmpty() && !(AbstractDungeon.player.orbs.get(i) instanceof EmptyOrbSlot)) {
			    	slots++;
			    }
	        }
	        
			if(slots >= AbstractDungeon.player.maxOrbs - 1) {
		        AbstractDungeon.actionManager.addToBottom(new EvokeAllOrbsAction());
			}
        }
		
        this.isDone = true;
	}

}
