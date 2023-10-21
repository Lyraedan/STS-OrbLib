package orbLib.patches.defect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.StormPower;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = StormPower.class, method = "onUseCard", paramtypez = { AbstractCard.class, UseCardAction.class, })
public class DefectStormPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(StormPower __instance, AbstractCard card, UseCardAction action) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
				
		if (card.type == AbstractCard.CardType.POWER && __instance.amount > 0) {
			__instance.flash();
		      for (int i = 0; i < __instance.amount; i++)
		        AbstractDungeon.actionManager.addToBottom((new ExtendedChannelAction(new DefectLightningOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL))); 
		    } 
		
		return SpireReturn.Return();
	}
}
