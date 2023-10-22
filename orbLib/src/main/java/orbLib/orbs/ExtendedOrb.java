package orbLib.orbs;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.patches.HitboxRightClick;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;

import basemod.abstracts.CustomOrb;
import orbLib.OrbLib;
import orbLib.actions.AddOrbIntentAction;
import orbLib.actions.AddOrbIntentAllEnemiesAction;
import orbLib.actions.ReduceOrbUseageAction;
import orbLib.actions.RemoveOrbIntentAction;
import orbLib.actions.RemoveOrbIntentAllEnemies;
import orbLib.orbs.intents.OrbIntent;
import orbLib.util.OrbLibUtils;
import orbLib.util.OrbListenerAction.OrbListenerType;

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

	/**
	 * <summary> DO NOT OVERRIDE THIS FUNCTION, LEAVE THIS FUNCTION ALONE </summary>
	 **/
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

	/**
	 * <summary> DO NOT OVERRIDE THIS FUNCTION, LEAVE THIS FUNCTION ALONE </summary>
	 **/
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

	public void onVictory(boolean playerIsDying) {
	}

	/**
	 * <summary> DO NOT OVERRIDE THIS FUNCTION </summary>
	 **/
	public void OnLoseEffectOverTime() {
		AbstractDungeon.actionManager.addToBottom(new ReduceOrbUseageAction(this));
	}

	/**
	 * <summary> Render appropriately colored text based on a base value and a final
	 * calculated value </summary>
	 **/
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

	/**
	 * <summary> LEAVE THIS FUNCTION ALONE </summary>
	 **/
	public void drawEffectAmount(SpriteBatch sb) {
		drawString(sb, Integer.toString(this.effectAmount), 0, -25, this.c);
	}

	/**
	 * <summary> DO NOT OVERRIDE THIS FUNCTION </summary>
	 **/
	public void reduceEffectiveness(int amount) {
		this.effectAmount -= amount;
	}

	/**
	 * <summary> DO NOT OVERRIDE THIS FUNCTION </summary>
	 **/
	public void increaseEffectiveness(int amount) {
		this.effectAmount += amount;
	}

	/**
	 * <summary> Calculate damage based on if the player has strength is weak and if
	 * the target has vulnerable or has flight </summary>
	 **/
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

	/**
	 * <summary> Calculate block based on frail and deterity. </summary>
	 **/
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
	 * @@Deprecated Used for debugging
	 * @param sb
	 */
	@Deprecated
	public void RenderOrbIndexes(SpriteBatch sb) {
		for (int i = 0; i < AbstractDungeon.player.orbs.size(); i++) {
			AbstractOrb orb = AbstractDungeon.player.orbs.get(i);
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(i),
					(orb.cX - 25) + NUM_X_OFFSET, (orb.cY - 25) + this.bobEffect.y / 2.0F + NUM_Y_OFFSET, this.c,
					this.fontScale);
		}
	}

	// HELPER FUNCTIONS

	/**
	 * <summary> Remove this orb. Not evoked. </summary>
	 **/
	public void remove() {
		int orbIndex = OrbLibUtils.GetOrbIndex(this);
		OrbLibUtils.removeOrbAt(orbIndex);
	}

	/**
	 * <summary> Evoke this orb </summary>
	 **/
	public void evoke() {
		int orbIndex = OrbLibUtils.GetOrbIndex(this);
		OrbLibUtils.evokeOrbAt(orbIndex);
	}

	/**
	 * <summary> Remove a listener of a type </summary>
	 **/
	public void RemoveOrbListener(Class<?> orbClass, OrbListenerType type) {
		OrbLib.orbListener.RemoveListener(orbClass, type);
	}

	/**
	 * <summary> Remove every single listener from this orb type </summary>
	 **/
	public void RemoveAllOrbListeners() {
		boolean orbStillExists = OrbLibUtils.OrbExists(getClass());
		if (!orbStillExists) {
			OrbLib.orbListener.RemoveAllListeners(getClass());
		}
	}

	/**
	 * <summary> Removed all listeners of a specific type from the current orb
	 * </summary>
	 **/
	public void RemoveAllOrbListenersOfType(OrbListenerType type) {
		OrbLib.orbListener.RemoveAllListenersOfType(getClass(), type);
	}

	/**
	 * <summary> Orb is first channelled orb. </summary>
	 **/
	public boolean isFirst() {
		return AbstractDungeon.player.orbs.get(0).equals(this);
	}

	/**
	 * <summary> Orb is first channelled orb of its type. </summary>
	 **/
	public boolean isFirstOfCurrentType() {
		ArrayList<AbstractOrb> orbs = OrbLibUtils.GetOrbsOfType(getClass());
		return orbs.get(0).equals(this);
	}

	/**
	 * <summary> Orb is the last channelled orb. </summary>
	 **/
	public boolean isLast() {
		ArrayList<AbstractOrb> orbs = OrbLibUtils.GetOrbsNotEmpty();
		return orbs.size() == 1;
	}

	/**
	 * <summary> Orb is last channelled orb of its type. </summary>
	 **/
	public boolean isLastOfCurrentType() {
		ArrayList<AbstractOrb> orbs = OrbLibUtils.GetOrbsOfType(getClass());
		return orbs.size() == 1;
	}

	/*
	 * Apply the orbs intent to the target enemy
	 **/
	public void addIntent(AbstractCreature target, OrbIntent orbIntent) {
		AbstractDungeon.actionManager.addToBottom(new AddOrbIntentAction(target, orbIntent));
	}

	/**
	 * <summary> Apply the orbs intent to all enemies </summary>
	 */
	public void addIntentAllEnemies(OrbIntent orbIntent) {
		AbstractDungeon.actionManager.addToBottom(new AddOrbIntentAllEnemiesAction(orbIntent));
	}

	/*
	 * Remove the orbs intent from the target enemy
	 **/
	public void removeIntent(AbstractCreature target, OrbIntent orbIntent) {
		AbstractDungeon.actionManager.addToBottom(new RemoveOrbIntentAction(target, orbIntent));
	}

	/**
	 * <summary> Remove the orbs intent from all enemies </summary>
	 */
	public void removeIntentAllEnemies(OrbIntent orbIntent) {
		AbstractDungeon.actionManager.addToBottom(new RemoveOrbIntentAllEnemies(orbIntent));
	}

}
