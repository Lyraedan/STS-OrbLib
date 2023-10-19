package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.Chaos;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.util.OrbLibUtils;

@SpirePatch(clz = Chaos.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectChaosPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Chaos __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		if (__instance.upgraded)
		      AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ExtendedChannelAction(OrbLibUtils.getRandomDefectOrb(true), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL)); 
			  AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ExtendedChannelAction(OrbLibUtils.getRandomDefectOrb(true), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		return SpireReturn.Return();
	}
}
