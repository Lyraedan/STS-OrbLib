package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import orbLib.OrbLib;
import orbLib.orbs.ExtendedOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "evokeOrb")
public class OrbOnEvokePatch {
	@SpireInsertPatch(rloc=2)
	public static void Insert(AbstractPlayer __instance) {
		if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(0) instanceof EmptyOrbSlot)) {
			if (__instance.orbs.get(0) instanceof ExtendedOrb) {
				OrbLib.orbListener.OnOrbEvoked(((ExtendedOrb) __instance.orbs.get(0)).getClass());
			}
		}
	}
}

@SpirePatch(clz = AbstractPlayer.class, method = "evokeNewestOrb")
class EvokeNewestOrbPatch {
	@SpirePostfixPatch
	public static void Insert(AbstractPlayer __instance) {
		if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(__instance.orbs.size() - 1) instanceof EmptyOrbSlot)) {
			if (__instance.orbs.get(__instance.orbs.size() - 1) instanceof ExtendedOrb) {
				OrbLib.orbListener.OnOrbEvoked(((ExtendedOrb) __instance.orbs.get(__instance.orbs.size() - 1)).getClass());
			}
		}
	}
}

@SpirePatch(clz = AbstractPlayer.class, method = "evokeWithoutLosingOrb")
class EvokeWithoutLosingOrbPatch {
	@SpirePostfixPatch
	public static void Insert(AbstractPlayer __instance) {
		if (!__instance.orbs.isEmpty() && !(__instance.orbs.get(0) instanceof EmptyOrbSlot)) {
			if (__instance.orbs.get(0) instanceof ExtendedOrb) {
				OrbLib.orbListener.OnOrbEvoked(((ExtendedOrb) __instance.orbs.get(0)).getClass());
			}
		}
	}
}