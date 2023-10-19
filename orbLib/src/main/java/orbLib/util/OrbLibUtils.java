package orbLib.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import orbLib.OrbLib;
import orbLib.orbs.DefectDarkOrb;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;
import orbLib.orbs.DefectPlasmaOrb;
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
	 * */
	public static ExtendedOrb getRandomDefectOrb(boolean useCardRng) {
	    ArrayList<ExtendedOrb> orbs = new ArrayList<>();
	    orbs.add(new DefectDarkOrb());
	    orbs.add(new DefectFrostOrb());
	    orbs.add(new DefectLightningOrb());
	    orbs.add(new DefectPlasmaOrb());
	    if (useCardRng)
	      return orbs.get(AbstractDungeon.cardRandomRng.random(orbs.size() - 1)); 
	    return orbs.get(MathUtils.random(orbs.size() - 1));
	  }
	
	public static void removeOrbAt(int orbIndex) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractOrb orb = player.orbs.get(orbIndex);

		if (!player.orbs.isEmpty() && !(orb instanceof EmptyOrbSlot)) {
			EmptyOrbSlot emptyOrbSlot = new EmptyOrbSlot(orb.cX, orb.cY);

			if(orb instanceof ExtendedOrb) {
				((ExtendedOrb) orb).onRemoved();
				OrbLib.orbListener.OnOrbRemoved(orb.getClass());
			}
			
			player.orbs.set(orbIndex, emptyOrbSlot);

			for (int i = orbIndex; i < player.orbs.size() - 1; i++) {
				Collections.swap(player.orbs, i, i + 1);
			}

			for (int i = 0; i < player.orbs.size(); i++) {
				((AbstractOrb) player.orbs.get(i)).setSlot(i, player.maxOrbs);
			}
		}
	}

	public static void evokeOrbAt(int orbIndex) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractOrb orb = player.orbs.get(orbIndex);

		if (!player.orbs.isEmpty() && !(orb instanceof EmptyOrbSlot)) {
			EmptyOrbSlot emptyOrbSlot = new EmptyOrbSlot(orb.cX, orb.cY);

			orb.onEvoke();
			player.orbs.set(orbIndex, emptyOrbSlot);

			for (int i = orbIndex; i < player.orbs.size() - 1; i++) {
				Collections.swap(player.orbs, i, i + 1);
			}

			for (int i = 0; i < player.orbs.size(); i++) {
				((AbstractOrb) player.orbs.get(i)).setSlot(i, player.maxOrbs);
			}
		}
	}
	
	public static int removeOrbsOfType(Class<?> orbClass) {
		List<AbstractOrb> orbs = GetOrbsOfType(orbClass);
		
		for(int i = 0; i < orbs.size(); i++) {
			int index = GetOrbIndex(orbs.get(i));
			removeOrbAt(index);
		}
		return orbs.size(); // Return the count incase a user wants to do something for every removed orb
	}
	
	public static int evokeOrbsOfType(Class<?> orbClass) {
		List<AbstractOrb> orbs = GetOrbsOfType(orbClass);
		
		for(int i = 0; i < orbs.size(); i++) {
			int index = GetOrbIndex(orbs.get(i));
			evokeOrbAt(index);
		}
		return orbs.size(); // Return the count incase a user wants to do something for every removed orb
	}
	
	public static int evokeOrbsOfTypeDontRemove(Class<?> orbClass) {
		List<AbstractOrb> orbs = GetOrbsOfType(orbClass);
		
		for(int i = 0; i < orbs.size(); i++) {
			int index = GetOrbIndex(orbs.get(i));
			evokeOrbAtDontRemove(index);
		}
		return orbs.size(); // Return the count incase a user wants to do something for every removed orb
	}

	public static void evokeOrbAtDontRemove(int orbIndex) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractOrb orb = player.orbs.get(orbIndex);

		if (!player.orbs.isEmpty() && !(orb instanceof EmptyOrbSlot)) {
			orb.onEvoke();
		}
	}

	public static int GetOrbIndex(AbstractOrb orb) {
		int index = 0; // First
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			AbstractOrb slot = AbstractDungeon.player.orbs.get(i);
			if (!(slot instanceof EmptyOrbSlot)) {
				if (slot.equals(orb)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	public static boolean OrbExists(Class<?> orbClass) {
		boolean result = false;
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			String name = AbstractDungeon.player.orbs.get(i).getClass().getSimpleName();
			if (name.equals(orbClass.getSimpleName())) {
				result = true;
				break;
			}
		}
		return result;
	}
	
	public static ArrayList<AbstractOrb> GetOrbsOfType(Class<?> orbClass) {
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			AbstractOrb orb = AbstractDungeon.player.orbs.get(i);
			if(orb instanceof ExtendedOrb) {
				if(orb.getClass().equals(orbClass)) {
					orbs.add(orb);
				}
			}
		}
		return orbs;
	}

	public static ArrayList<AbstractOrb> GetOrbsNotEmpty() {
		ArrayList<AbstractOrb> orbs = new ArrayList<AbstractOrb>();
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			AbstractOrb orb = AbstractDungeon.player.orbs.get(i);
			if (!(orb instanceof EmptyOrbSlot)) {
				orbs.add(orb);
			}
		}
		return orbs;
	}

}
