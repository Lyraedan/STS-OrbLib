package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.ColdSnap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = ColdSnap.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectColdSnapPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(ColdSnap __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, __instance.damage, __instance.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	    for (int i = 0; i < __instance.magicNumber; i++) {
	    	AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL)); 
	    }
		return SpireReturn.Return();
	}
}
