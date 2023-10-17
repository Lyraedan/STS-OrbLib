package orbLib.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.CrackedCore;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = CrackedCore.class, method = "atPreBattle")
public class CrackedCorePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(CrackedCore __instance) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectLightningOrb(), !isDefect));
		return SpireReturn.Return();
	}
}
