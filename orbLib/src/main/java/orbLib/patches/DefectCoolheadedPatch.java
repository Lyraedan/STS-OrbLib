package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.blue.Coolheaded;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectFrostOrb;

@SpirePatch(clz = Coolheaded.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectCoolheadedPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Coolheaded __instance, AbstractPlayer p, AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), !isDefect));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, __instance.magicNumber));
		return SpireReturn.Return();
	}
}