package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectZapOrb;

@SpirePatch(clz = Zap.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectZapPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Zap __instance, AbstractPlayer p, AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		for (int i = 0; i < __instance.magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectZapOrb(), !isDefect));
		}
		return SpireReturn.Return();
	}
}
