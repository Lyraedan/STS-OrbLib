package orbLib.patches.relics;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.FrozenCore;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = FrozenCore.class, method = "onPlayerEndTurn")
public class FrozenCorePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(FrozenCore __instance) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;

		if (AbstractDungeon.player.hasEmptyOrb()) {
			__instance.flash();
			AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), !isDefect));
		}

		return SpireReturn.Return();
	}
}
