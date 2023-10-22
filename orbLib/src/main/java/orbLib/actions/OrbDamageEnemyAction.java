package orbLib.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.orbs.ExtendedOrb;
import orbLib.orbs.intents.OrbIntentAggressive;

public class OrbDamageEnemyAction extends AbstractGameAction {
	  private DamageInfo info;
	  
	  private ExtendedOrb orb;
	  
	  public OrbDamageEnemyAction(ExtendedOrb orb, AbstractCreature orbTarget, DamageInfo info, AbstractGameAction.AttackEffect effect) {
	    this.info = info;
	    this.actionType = AbstractGameAction.ActionType.DAMAGE;
	    this.attackEffect = effect;
	    this.orb = orb;
	    this.target = orbTarget;
	  }
	  
	  public void update() {
		  if(this.target == null || target.isDeadOrEscaped()) {
			this.target = orb.getRandomTarget();
			this.orb.orbTarget = this.target;
		  }
		  if (this.target != null) {
			  addToTop(new ExtendedDamageAction(this.target, this.info, this.attackEffect));
		  }
		  this.isDone = true;
	  	}
	}
