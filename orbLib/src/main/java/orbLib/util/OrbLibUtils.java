package orbLib.util;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import orbLib.orbs.DefectDarkOrb;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;
import orbLib.orbs.ExtendedOrb;

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
	
	/**
	 * <summery>
	 * Get a random defect orb
	 * </summery>
	 * 
	 * */
	public static ExtendedOrb getRandomOrb(boolean useCardRng) {
	    ArrayList<ExtendedOrb> orbs = new ArrayList<>();
	    orbs.add(new DefectDarkOrb());
	    orbs.add(new DefectFrostOrb());
	    orbs.add(new DefectLightningOrb());
	    //orbs.add(new Plasma());
	    if (useCardRng)
	      return orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)); 
	    return orbs.get(MathUtils.random(orbs.size() - 1));
	  }

}
