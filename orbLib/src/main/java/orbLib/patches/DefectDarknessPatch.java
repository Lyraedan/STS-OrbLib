package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Darkness;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.DarkImpulsePatchedAction;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectDarkOrb;

@SpirePatch(clz = Darkness.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectDarknessPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Darkness __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
				
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectDarkOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
	    if (__instance.upgraded)
	    	AbstractDungeon.actionManager.addToBottom(new DarkImpulsePatchedAction()); 
		
		return SpireReturn.Return();
	}
}
