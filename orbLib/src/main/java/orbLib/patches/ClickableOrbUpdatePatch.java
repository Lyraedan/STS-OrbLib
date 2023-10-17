package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import javassist.CtBehavior;
import orbLib.orbs.ExtendedOrb;

@SpirePatch(clz = AbstractOrb.class, method = "update")
public class ClickableOrbUpdatePatch {

	@SpireInsertPatch(locator=Locator.class)
	public static void Insert(AbstractOrb __instance) {
		if(__instance instanceof ExtendedOrb) {
			((ExtendedOrb) __instance).clickUpdate();
		}
	}

	private static class Locator extends SpireInsertLocator {
		@Override
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
			Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "update");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}