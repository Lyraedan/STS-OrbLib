package orbLib.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.LineFinder;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertLocator;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import javassist.CtBehavior;
import orbLib.orbs.intents.OrbIntent;

@SpirePatch(clz = AbstractMonster.class, method = "render", paramtypez = { SpriteBatch.class })
public class AbstractMonsterIntentPatch {
	@SpireInsertPatch(locator=Locator.class)
	public static void Insert(AbstractMonster __instance, SpriteBatch sb) {
		if(OrbIntentsPatch.orbIntents.get(__instance).isEmpty()) {
			return;
		}
		
		for(int i = 0; i < OrbIntentsPatch.orbIntents.get(__instance).size(); i++) {
			OrbIntent intent = OrbIntentsPatch.orbIntents.get(__instance).get(i);
			intent.render(sb, i * 36, 0);
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

@SpirePatch(clz = GameActionManager.class, method = "getNextAction")
class ClearOrbIntentPatch {
	@SpirePostfixPatch
	public static void ClearOrbIntents(GameActionManager __instance) {
		boolean isMonstersTurn = !__instance.monsterQueue.isEmpty();
		if(isMonstersTurn) {
			for(int i = 0; i < __instance.monsterQueue.size(); i++) {
				AbstractMonster monster = __instance.monsterQueue.get(i).monster;
				OrbIntentsPatch.orbIntents.get(monster).clear();
			}
		}
	}
}