package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.orbs.ExtendedOrb;

public class ExtendedDamageEnemyAction extends AbstractGameAction {
	  private DamageInfo info;
	  
	  private ExtendedOrb orb;
	  
	  public ExtendedDamageEnemyAction(ExtendedOrb orb, AbstractCreature orbTarget, DamageInfo info, AbstractGameAction.AttackEffect effect) {
	    this.info = info;
	    this.actionType = AbstractGameAction.ActionType.DAMAGE;
	    this.attackEffect = effect;
	    this.orb = orb;
	    this.target = orbTarget;
	  }
	  
	  public void update() {
		  if(this.target == null || target.isDeadOrEscaped()) {
			this.target = (AbstractCreature)AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
			this.orb.orbTarget = this.target;
		  }
		  if (this.target != null)
			  addToTop(new ExtendedDamageAction(this.target, this.info, this.attackEffect)); 
		  this.isDone = true;
	  	}
	}