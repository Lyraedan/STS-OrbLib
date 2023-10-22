package orbLib.patches;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import orbLib.orbs.intents.OrbIntent;

@SpirePatch(clz = AbstractCreature.class, method = "renderHealth")
public class AbstractCreatureIntentPatch {
	@SpireInsertPatch(localvars = { "x", "y" }, locator=Locator.class)
	public static void DrawOrbIntents(AbstractCreature __instance, SpriteBatch sb, float x, float y) {
		if (OrbIntentsPatch.orbIntents.get(__instance).isEmpty()) {
			return;
		}

		for (int i = 0; i < OrbIntentsPatch.orbIntents.get(__instance).size(); i++) {
			OrbIntent intent = OrbIntentsPatch.orbIntents.get(__instance).get(i);
			intent.render(sb, x + i * 48, y);
		}
	}

	private static class Locator extends SpireInsertLocator {

		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
			Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCreature.class, "renderPowerIcons");
			return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
		}
	}
}

@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
class ClearOrbIntentPatch {
	@SpirePostfixPatch
	public static void ClearOrbIntents(GameActionManager __instance) {
		boolean isMonstersTurn = !__instance.monsterQueue.isEmpty();
		if (isMonstersTurn) {
			for (int i = 0; i < __instance.monsterQueue.size(); i++) {
				AbstractMonster monster = __instance.monsterQueue.get(i).monster;
				OrbIntentsPatch.orbIntents.get(monster).clear();
			}
		}
	}
}
