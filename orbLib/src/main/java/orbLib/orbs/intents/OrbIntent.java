package orbLib.orbs.intents;

import static orbLib.OrbLib.makeOrbIntentPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BobEffect;

import orbLib.util.TextureLoader;

public abstract class OrbIntent {

	public enum Intents {
		AGGRESSIVE, DEFENSIVE, DEBUFF, BUFF, SLEEPING, STUNNED, UNKNOWN
	}

	public Intents intent = Intents.UNKNOWN;
	public AbstractCreature target; // Null = all enemies
	public Texture img;
	public int amount = 0;

	private final Color textColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private final Color tintColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private BobEffect bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
	protected float fontScale = 0.4F; // 0.7

	public OrbIntent(AbstractCreature target, int amount, Intents intent) {
		this.target = target;
		this.amount = amount;
		this.intent = intent;
		ReloadImage();
	}

	public void ReloadImage() {
		this.img = TextureLoader.getTexture(makeOrbIntentPath(GetIntentResource()));
	}

	public void render(SpriteBatch sb, float xOff, float yOff) {
		bobEffect.update();
		sb.setColor(tintColor);
		float x = target == null ? xOff : target.drawX + xOff;
		float y = target == null ? yOff : (target.drawY + yOff) + (bobEffect.y / 2.0f);
		float angle = 0f;
		if (target instanceof AbstractMonster) {
			x = (((AbstractMonster) target).intentHb.cX + xOff) - 64.0f;
			y = (((AbstractMonster) target).intentHb.cY + yOff) - 72.0f + (bobEffect.y / 2.0f);
			sb.draw(this.img, x, y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 1.2F, Settings.scale * 1.2F, angle,
					0, 0, 128, 128, false, false);
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amount), x + 15, y + 88.0f,
					textColor, this.fontScale);
		} else if (target instanceof AbstractPlayer) {
			float BLOCK_ICON_X = -14.0F * Settings.scale;
			float BLOCK_ICON_Y = -80.0F * Settings.scale;
			float HB_Y_OFFSET_DIST = 12.0F * Settings.scale;
			float hbYOffset = HB_Y_OFFSET_DIST * 5.0F;
			
			x = AbstractDungeon.player.hb.cX - AbstractDungeon.player.hb.width / 2.0F;
		    y = AbstractDungeon.player.hb.cY - AbstractDungeon.player.hb.height / 2.0F + hbYOffset + (bobEffect.y / 2f);
			
			sb.draw(this.img, x + BLOCK_ICON_X, y + BLOCK_ICON_Y, 32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
			FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amount), x, y - 64.0f,
					textColor, this.fontScale);
		}
	}

	public abstract String GetIntentResource();

	public boolean amountBetweenRanges(int min, int max) {
		return amount >= min && amount <= max;
	}
	
	public abstract OrbIntent makeCopy();

}
