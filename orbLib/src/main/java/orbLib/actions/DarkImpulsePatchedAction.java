package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.orbs.DefectDarkOrb;

public class DarkImpulsePatchedAction extends AbstractGameAction {
  public void update() {
    if (this.duration == Settings.ACTION_DUR_FAST && 
      !AbstractDungeon.player.orbs.isEmpty()) {
      for (AbstractOrb o : AbstractDungeon.player.orbs) {
        if (o instanceof com.megacrit.cardcrawl.orbs.Dark) {
          o.onStartOfTurn();
          o.onEndOfTurn();
        } 
      } 
      if (AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof com.megacrit.cardcrawl.orbs.EmptyOrbSlot))
        if (AbstractDungeon.player.orbs.get(0) instanceof DefectDarkOrb) {
          ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onStartOfTurn();
          ((AbstractOrb)AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
        }  
    } 
    tickDuration();
  }
}
