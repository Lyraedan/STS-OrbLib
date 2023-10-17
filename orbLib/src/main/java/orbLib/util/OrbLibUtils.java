package orbLib.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

public class OrbLibUtils {

	public static void increaseMaxOrbSlotsCapless(int amount, boolean playSfx) {
		if (playSfx)
			CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
		AbstractDungeon.player.maxOrbs += amount;
		int i;
		for (i = 0; i < amount; i++)
			AbstractDungeon.player.orbs.add(new EmptyOrbSlot());
		for (i = 0; i < AbstractDungeon.player.orbs.size(); i++)
			((AbstractOrb) AbstractDungeon.player.orbs.get(i)).setSlot(i, AbstractDungeon.player.maxOrbs);
	}

}
