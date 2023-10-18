package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.TriggerEndOfTurnOrbsAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.orbs.ExtendedOrb;

/// INSERT PATCH DIDN'T WORK RIGHT, IT LOOKED BROKEN :(
@SpirePatch(clz = TriggerEndOfTurnOrbsAction.class, method = "update")
public class OrbLoseEffectOverTimePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> Insert(TriggerEndOfTurnOrbsAction __instance) {
		if (!AbstractDungeon.player.orbs.isEmpty()) {
			if (AbstractDungeon.player.hasRelic("Cables") && !(AbstractDungeon.player.orbs.get(0) instanceof com.megacrit.cardcrawl.orbs.EmptyOrbSlot)) {
				((AbstractOrb) AbstractDungeon.player.orbs.get(0)).onEndOfTurn();
			}
			
			for (AbstractOrb o : AbstractDungeon.player.orbs) {
				o.onEndOfTurn();
				if (o instanceof ExtendedOrb) {
					ExtendedOrb orb = (ExtendedOrb) o;
					if (orb.loseEffectOverTime) {
						orb.OnLoseEffectOverTime();
					}
				}
			}
		}

		if (!AbstractDungeon.player.orbs.isEmpty()) {
			for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
				
			}
		}
		__instance.isDone = true;
		return SpireReturn.Return();
	}
}
