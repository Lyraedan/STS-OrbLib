package orbLib.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.helpers.Hitbox;

import javassist.CtBehavior;
import orbLib.orbs.intents.OrbIntent;

@SpirePatch(clz = AbstractPlayer.class, method = "render", paramtypez = { SpriteBatch.class })
public class AbstractPlayerIntentPatch {
	@SpireInsertPatch(locator=Locator.class)
	public static void Insert(AbstractPlayer __instance, SpriteBatch sb) {
		if(OrbIntentsPatch.orbIntents.get(__instance).isEmpty()) {
			return;
		}
		
		for(int i = 0; i < OrbIntentsPatch.orbIntents.get(__instance).size(); i++) {
			OrbIntent intent = OrbIntentsPatch.orbIntents.get(__instance).get(i);
			intent.render(sb, i * 48, 0); // What? Overlapping
		}
	}

	private static class Locator extends SpireInsertLocator {
		@Override
		public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
			Matcher finalMatcher = new Matcher.MethodCallMatcher(Hitbox.class, "render");
			return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
		}
	}
}

/*
@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
class ClearPlayerOrbIntentPatch {
	@SpirePostfixPatch
	public static void ClearOrbIntents(GameActionManager __instance) {
		OrbIntentsPatch.orbIntents.get(AbstractDungeon.player).clear();
	}
}*/