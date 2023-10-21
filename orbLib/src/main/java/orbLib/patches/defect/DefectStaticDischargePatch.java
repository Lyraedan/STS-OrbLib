package orbLib.patches.defect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StaticDischargePower;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = StaticDischargePower.class, method = "onAttacked", paramtypez = { DamageInfo.class, int.class, })
public class DefectStaticDischargePatch {
	@SpirePrefixPatch
	public static SpireReturn<Integer> ReplaceWithUpdatedOrb(StaticDischargePower __instance, DamageInfo info, int damageAmount) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != __instance.owner && damageAmount > 0) {
			__instance.flash();
		      for (int i = 0; i < __instance.amount; i++)
		        AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectLightningOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL)); 
		    } 
		
		return SpireReturn.Return(damageAmount);
	}
}
