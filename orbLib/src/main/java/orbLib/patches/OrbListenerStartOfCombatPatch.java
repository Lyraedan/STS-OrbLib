package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;

import orbLib.OrbLib;

@SpirePatch(clz = AbstractPlayer.class, method = "applyStartOfCombatLogic")
public class OrbListenerStartOfCombatPatch {
	@SpirePostfixPatch
	public static void Insert(AbstractPlayer __instance) {
		OrbLib.orbListener.queue.clear();
	}
}