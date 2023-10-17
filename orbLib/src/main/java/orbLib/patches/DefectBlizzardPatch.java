package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.ByRef;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.blue.Blizzard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.BlizzardEffect;

import orbLib.orbs.DefectFrostOrb;

@SpirePatch(clz = Blizzard.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectBlizzardPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Blizzard __instance, AbstractPlayer p, AbstractMonster m) {		
		int frostCount = 0;
	    for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
	      if (o instanceof DefectFrostOrb)
	        frostCount++; 
	    } 
	    __instance.baseDamage = frostCount * __instance.magicNumber;
	    __instance.calculateCardDamage((AbstractMonster)null);
	    if (Settings.FAST_MODE) {
	      AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction((AbstractGameEffect)new BlizzardEffect(frostCount, 
	              AbstractDungeon.getMonsters().shouldFlipVfx()), 0.25F));
	    } else {
	    	AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new VFXAction((AbstractGameEffect)new BlizzardEffect(frostCount, AbstractDungeon.getMonsters().shouldFlipVfx()), 1.0F));
	    } 
	    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, __instance.multiDamage, __instance.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_HEAVY, false));
		
		return SpireReturn.Return();
	}
}

@SpirePatch(clz = Blizzard.class, method = "applyPowers")
class DefectBlizzardApplyPowersPatch {

	@SpireInsertPatch(localvars={"frostCount"}, rloc=5)
	public static void Insert(Blizzard __instance, @ByRef int[] vars) {
		for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
		      if (o instanceof DefectFrostOrb)
		        vars[0]++; 
		    } 
	}
}
