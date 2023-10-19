package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.orbs.ExtendedOrb;

@SpirePatch(clz = AbstractPlayer.class, method = "onVictory")
public class OrbOnVictoryPatch {
	@SpirePrefixPatch
	public static void Insert(AbstractPlayer __instance) {
		if (!__instance.orbs.isEmpty()) {
			for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
				if(AbstractDungeon.player.orbs.get(i) instanceof ExtendedOrb) {
					((ExtendedOrb) AbstractDungeon.player.orbs.get(i)).onVictory(__instance.isDying);
				}
			}
		}
	}
}