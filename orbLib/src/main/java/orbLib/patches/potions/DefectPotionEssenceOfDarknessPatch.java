package orbLib.patches.potions;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.EssenceOfDarkness;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import orbLib.actions.DefectEssenceOfDarknessPatchedAction;

@SpirePatch(clz = EssenceOfDarkness.class, method = "use", paramtypez = { AbstractCreature.class })
public class DefectPotionEssenceOfDarknessPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(EssenceOfDarkness __instance, AbstractCreature target) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
			AbstractDungeon.actionManager.addToBottom(
					(AbstractGameAction) new DefectEssenceOfDarknessPatchedAction(__instance.getPotency(), !isDefect));
		}
		return SpireReturn.Return();
	}
}
