package orbLib.patches.potions;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.potions.PotionOfCapacity;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import orbLib.util.OrbLibUtils;

@SpirePatch(clz = PotionOfCapacity.class, method = "use", paramtypez = { AbstractCreature.class })
public class DefectPotionOfCapacityPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(PotionOfCapacity __instance, AbstractCreature target) {		
		if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
			OrbLibUtils.increaseMaxOrbSlotsCapless(__instance.getPotency(), false);	
		}
		return SpireReturn.Return();
	}
}
