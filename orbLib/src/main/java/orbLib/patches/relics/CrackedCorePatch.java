package orbLib.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.CrackedCore;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = CrackedCore.class, method = "atPreBattle")
public class CrackedCorePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(CrackedCore __instance) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectLightningOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		return SpireReturn.Return();
	}
}
