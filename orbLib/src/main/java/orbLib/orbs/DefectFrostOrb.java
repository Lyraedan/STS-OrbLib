package orbLib.orbs;

import static orbLib.OrbLib.makeOrbPath;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbActivateEffect;
import com.megacrit.cardcrawl.vfx.combat.FrostOrbPassiveEffect;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;

import orbLib.orbs.intents.OrbIntentDefensive;

public class DefectFrostOrb extends ExtendedOrb {
	public static final String ORB_ID = "Frost";

	private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString("Frost");

	private boolean hFlip1;

	private boolean hFlip2;

	private float vfxTimer = 1.0F, vfxIntervalMin = 0.15F, vfxIntervalMax = 0.8F;

	private static final int PASSIVE_AMOUNT = 2;
	private static final int EVOKE_AMOUNT = 5;

	public DefectFrostOrb() {
		super(ORB_ID, orbString.NAME, PASSIVE_AMOUNT, EVOKE_AMOUNT, orbString.DESCRIPTION[0], orbString.DESCRIPTION[1],
				makeOrbPath("frost_orb.png"));

		this.hFlip1 = MathUtils.randomBoolean();
		this.hFlip2 = MathUtils.randomBoolean();
		this.ID = "Frost";
		this.name = orbString.NAME;
		this.baseEvokeAmount = EVOKE_AMOUNT;
		this.evokeAmount = this.baseEvokeAmount;
		this.basePassiveAmount = PASSIVE_AMOUNT;
		this.passiveAmount = this.basePassiveAmount;
		updateDescription();
		this.channelAnimTimer = 0.5F;
		addIntent(AbstractDungeon.player, new OrbIntentDefensive(AbstractDungeon.player, passiveAmount));
	}

	@Override
	public void onRightClick() {
		float speedTime = 0.6F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE)
			speedTime = 0.0F;
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new VFXAction(
				(AbstractGameEffect) new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
	}

	public void updateDescription() {
		applyFocus();
		this.description = orbString.DESCRIPTION[0] + this.passiveAmount + orbString.DESCRIPTION[1] + this.evokeAmount
				+ orbString.DESCRIPTION[2];
	}

	public void onEvoke() {
		AbstractDungeon.actionManager
				.addToTop((AbstractGameAction) new GainBlockAction((AbstractCreature) AbstractDungeon.player,
						(AbstractCreature) AbstractDungeon.player, this.evokeAmount));
		removeIntent(AbstractDungeon.player, new OrbIntentDefensive(AbstractDungeon.player, passiveAmount));
	}

	public void updateAnimation() {
		super.updateAnimation();
		this.angle += Gdx.graphics.getDeltaTime() * 180.0F;
		this.vfxTimer -= Gdx.graphics.getDeltaTime();
		if (this.vfxTimer < 0.0F) {
			AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			if (MathUtils.randomBoolean())
				AbstractDungeon.effectList.add(new FrostOrbPassiveEffect(this.cX, this.cY));
			this.vfxTimer = MathUtils.random(this.vfxIntervalMin, this.vfxIntervalMax);
		}
	}

	public void onEndOfTurn() {
		float speedTime = 0.6F / AbstractDungeon.player.orbs.size();
		if (Settings.FAST_MODE)
			speedTime = 0.0F;
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new VFXAction(
				(AbstractGameEffect) new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.FROST), speedTime));
		AbstractDungeon.actionManager
				.addToBottom((AbstractGameAction) new GainBlockAction((AbstractCreature) AbstractDungeon.player,
						(AbstractCreature) AbstractDungeon.player, this.passiveAmount, true));
	}

	public void triggerEvokeAnimation() {
		CardCrawlGame.sound.play("ORB_FROST_EVOKE", 0.1F);
		AbstractDungeon.effectsQueue.add(new FrostOrbActivateEffect(this.cX, this.cY));
	}

	public void render(SpriteBatch sb) {
		sb.setColor(this.c);
		sb.draw(ImageMaster.FROST_ORB_RIGHT, this.cX - 48.0F + this.bobEffect.y / 4.0F,
				this.cY - 48.0F + this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0,
				0, 96, 96, this.hFlip1, false);
		sb.draw(ImageMaster.FROST_ORB_LEFT, this.cX - 48.0F + this.bobEffect.y / 4.0F,
				this.cY - 48.0F - this.bobEffect.y / 4.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0,
				0, 96, 96, this.hFlip1, false);
		sb.draw(ImageMaster.FROST_ORB_MIDDLE, this.cX - 48.0F - this.bobEffect.y / 4.0F,
				this.cY - 48.0F + this.bobEffect.y / 2.0F, 48.0F, 48.0F, 96.0F, 96.0F, this.scale, this.scale, 0.0F, 0,
				0, 96, 96, this.hFlip2, false);
		renderText(sb);
		this.hb.render(sb);
	}

	public void playChannelSFX() {
		CardCrawlGame.sound.play("ORB_FROST_CHANNEL", 0.1F);
	}

	public AbstractOrb makeCopy() {
		return new Frost();
	}

	@Override
	public void onRemoved() {
		
	}
}
