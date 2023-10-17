package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import orbLib.orbs.DefectLightningOrb;

public class TempestPatchedAction extends AbstractGameAction {
	private boolean freeToPlayOnce = false;

	private AbstractPlayer p;

	private int energyOnUse = -1;

	private boolean upgraded;
	private boolean evokeAllOnFull = false;

	public TempestPatchedAction(AbstractPlayer p, int energyOnUse, boolean upgraded, boolean freeToPlayOnce, boolean evokeAllOnFull) {
	    this.p = p;
	    this.duration = Settings.ACTION_DUR_XFAST;
	    this.actionType = AbstractGameAction.ActionType.SPECIAL;
	    this.energyOnUse = energyOnUse;
	    this.upgraded = upgraded;
	    this.freeToPlayOnce = freeToPlayOnce;
	    this.evokeAllOnFull = evokeAllOnFull;
	  }

	public void update() {
		int effect = EnergyPanel.totalCount;
		if (this.energyOnUse != -1)
			effect = this.energyOnUse;
		if (this.p.hasRelic("Chemical X")) {
			effect += 2;
			this.p.getRelic("Chemical X").flash();
		}
		if (this.upgraded)
			effect++;
		if (effect > 0) {
			for (int i = 0; i < effect; i++) {
				DefectLightningOrb lightning = new DefectLightningOrb();
				addToBot(new ExtendedChannelAction(lightning, evokeAllOnFull));
			}
			if (!this.freeToPlayOnce)
				this.p.energy.use(EnergyPanel.totalCount);
		}
		this.isDone = true;
	}
}
