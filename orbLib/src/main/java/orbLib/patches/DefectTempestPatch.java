package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.Tempest;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.actions.TempestPatchedAction;

@SpirePatch(clz = Tempest.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectTempestPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Tempest __instance, AbstractPlayer p, AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TempestPatchedAction(p, __instance.energyOnUse, __instance.upgraded, __instance.freeToPlayOnce, !isDefect));
		
		return SpireReturn.Return();
	}
}
