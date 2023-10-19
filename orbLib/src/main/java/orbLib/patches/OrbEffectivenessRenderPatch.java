package orbLib.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.orbs.ExtendedOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "render", paramtypez = { SpriteBatch.class })
public class OrbEffectivenessRenderPatch {
	@SpireInsertPatch(rloc = 8)
	public static void Insert(AbstractPlayer __instance, SpriteBatch sb) {
		if (!__instance.orbs.isEmpty()) {
			for (int i = 0; i < __instance.orbs.size(); i++) {
				AbstractOrb orb = __instance.orbs.get(i);
				if (orb instanceof ExtendedOrb) {
					if (((ExtendedOrb) orb).loseEffectOverTime) {
						((ExtendedOrb) orb).drawEffectAmount(sb);
					}
				}
			}
		}
	}
}
