package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExtendedDamageRandomEnemyAction extends AbstractGameAction {
  private DamageInfo info;
  
  public ExtendedDamageRandomEnemyAction(DamageInfo info, AbstractGameAction.AttackEffect effect) {
    this.info = info;
    this.actionType = AbstractGameAction.ActionType.DAMAGE;
    this.attackEffect = effect;
  }
  
  public void update() {
    this.target = (AbstractCreature)AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
    if (this.target != null)
      addToTop(new ExtendedDamageAction(this.target, this.info, this.attackEffect)); 
    this.isDone = true;
  }
}
