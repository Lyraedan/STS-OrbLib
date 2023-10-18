package orbLib.orbs;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import basemod.abstracts.CustomOrb;
import orbLib.OrbLib;
import orbLib.actions.ReduceOrbUseageAction;

public abstract class ExtendedOrb extends CustomOrb {

	public int effectAmount = 0;
	public boolean loseEffectOverTime = false;

	public AbstractCreature orbTarget;

	public String className;

	public ExtendedOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription,
			String evokeDescription) {
		super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription);
		className = getClass().getSimpleName();
		OrbLib.orbListener.OnOrbChannelled(getClass());
	}

	public ExtendedOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription,
			String evokeDescription, String imgPath) {
		super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
		className = getClass().getSimpleName();
		OrbLib.orbListener.OnOrbChannelled(getClass());
	}

	public void clickUpdate() {
		if (this instanceof ExtendedOrb) {
			ExtendedOrb orb = (ExtendedOrb) this;
			if (HitboxRightClick.rightClicked.get(orb.hb)
					|| (Settings.isControllerMode && orb.hb.hovered && CInputActionSet.topPanel.isJustPressed())) {
				CInputActionSet.topPanel.unpress();
				OrbLib.orbListener.OnOrbRightClicked(getClass());
				onRightClick();
			}
		}
	}

	public boolean hovered() {
		if (this instanceof ExtendedOrb) {
			ExtendedOrb orb = (ExtendedOrb) this;
			return orb.hb.hovered;
		}
		return false;
	}

	public void onRightClick() {
	}
	
	public void onRemoved() {
	}

	public void OnLoseEffectOverTime() {
		AbstractDungeon.actionManager.addToBottom(new ReduceOrbUseageAction(this));
	}

	@Override
	public void render(SpriteBatch sb) {
		// RenderOrbIndexes(sb);
		if (this.loseEffectOverTime) {
			drawEffectAmount(sb);
		}
	}

	public void renderText(SpriteBatch sb, int passive, int evoke, float calculated) {
		int orbIndex = AbstractDungeon.player.orbs.indexOf(this);
		boolean isEmpty = (AbstractDungeon.player.orbs.get(orbIndex) instanceof EmptyOrbSlot);

		if (!isEmpty) {
			Color neutral = this.showEvokeValue ? new Color(0.2F, 1.0F, 1.0F, this.c.a) : this.c;
			Color negative = new Color(1.0f, 0.0f, 0.0f, this.c.a);
			Color positive = new Color(0.0f, 1.0f, 0.0f, this.c.a);
			Color textColor = neutral;
			int baseToUse = this.showEvokeValue ? evoke : passive;

			if (calculated < baseToUse) {
				textColor = negative;
			} else if (calculated > baseToUse) {
				textColor = positive;
			}

			if (this.showEvokeValue) {
				FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,

						Integer.toString((int) calculated), this.cX + NUM_X_OFFSET,
						this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, textColor, this.fontScale);
			} else {
				FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L,

						Integer.toString((int) calculated), this.cX + NUM_X_OFFSET,
						this.cY + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, textColor, this.fontScale);
			}
		}
	}

	public void drawString(SpriteBatch sb, String string, int xOff, int yOff, Color color) {
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, string, (this.cX + xOff) + NUM_X_OFFSET,
				(this.cY + yOff) + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, color, this.fontScale);
	}

	public void drawEffectAmount(SpriteBatch sb) {
		drawString(sb, Integer.toString(this.effectAmount), 0, -25, this.c);
	}

	public void reduceEffectiveness(int amount) {
		this.effectAmount -= amount;
	}

	public void increaseEffectiveness(int amount) {
		this.effectAmount += amount;
	}

	public float calculateDamage(int amount) {
		boolean isWeakened = AbstractDungeon.player.hasPower("Weakened");
		boolean targetIsVulnerable = this.orbTarget != null ? this.orbTarget.hasPower("Vulnerable") : false;
		boolean targetHasFlight = this.orbTarget != null ? this.orbTarget.hasPower("Flight") : false;

		int strMod = AbstractDungeon.player.hasPower("Strength") ? AbstractDungeon.player.getPower("Strength").amount
				: 0;
		float result = amount;

		if (targetIsVulnerable) {
			result *= 1.5f;
		}

		if (targetHasFlight) {
			result *= 0.5f;
		}

		if (isWeakened) {
			result *= 0.75f;
		}

		result += strMod;

		return result;
	}

	public float calculateBlock(int amount) {
		boolean hasDexterity = AbstractDungeon.player.hasPower("Dexterity");
		boolean isFrail = AbstractDungeon.player.hasPower("Frail");

		int dexMod = hasDexterity ? AbstractDungeon.player.getPower("Dexterity").amount : 0;
		float result = amount;

		if (isFrail) {
			result *= 0.75f;
		}

		result += dexMod;

		return result;
	}

	/***
	 * <summary> Used for debugging </summary>
	 * 
	 * @param sb
	 */
	public void RenderOrbIndexes(SpriteBatch sb) {
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			AbstractOrb orb = AbstractDungeon.player.orbs.get(i);
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(i),
					(orb.cX - 25) + NUM_X_OFFSET, (orb.cY - 25) + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c,
					this.fontScale);
		}
	}

	// HELPER FUNCTIONS

	public void remove() {
		int orbIndex = GetOrbIndex(this);
		removeOrbAt(orbIndex);
	}

	public void evoke() {
		int orbIndex = GetOrbIndex(this);
		evokeOrbAt(orbIndex);
	}

	public void removeOrbAt(int orbIndex) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractOrb orb = player.orbs.get(orbIndex);

		if (!player.orbs.isEmpty() && !(orb instanceof EmptyOrbSlot)) {
			EmptyOrbSlot emptyOrbSlot = new EmptyOrbSlot(orb.cX, orb.cY);

			if(orb instanceof ExtendedOrb) {
				onRemoved();
				OrbLib.orbListener.OnOrbRemoved(getClass());
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

	public void evokeOrbAt(int orbIndex) {
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

	public void evokeOrbAtDontRemove(int orbIndex) {
		AbstractPlayer player = AbstractDungeon.player;
		AbstractOrb orb = player.orbs.get(orbIndex);

		if (!player.orbs.isEmpty() && !(orb instanceof EmptyOrbSlot)) {
			orb.onEvoke();
		}
	}

	public int GetOrbIndex(AbstractOrb orb) {
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

	public void RemoveOrbListener(Class<?> orbClass) {
		boolean orbStillExists = OrbExists(orbClass.getSimpleName());
		if (!orbStillExists) {
			OrbLib.orbListener.RemoveAllListeners(orbClass);
		}
	}

	public boolean OrbExists(String orbName) {
		boolean result = false;
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			String name = AbstractDungeon.player.orbs.get(i).getClass().getSimpleName();
			if (name.equals(orbName)) {
				result = true;
				break;
			}
		}
		return result;
	}

	public ArrayList<AbstractOrb> GetOrbsNotEmpty() {
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
