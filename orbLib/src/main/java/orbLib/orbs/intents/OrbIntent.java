package orbLib.orbs.intents;

import static orbLib.OrbLib.makeOrbIntentPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BobEffect;

import orbLib.util.TextureLoader;

public abstract class OrbIntent {
	
	public enum Intents {
		AGGRESSIVE, DEFENSIVE, STRATEGIC_DEBUF, STRATEGIC_BUFF, AGGRESSIVE_DEBUFF, AGGRESSIVE_DEFENSIVE, AGGRESSIVE_BUFF, DEFENSIVE_BUFF, DEFENSIVE_DEBUFF, COWARDLY, SLEEPING, STUNNED, UNKNOWN
	}
	
	public Intents intent = Intents.UNKNOWN;
	public AbstractMonster target;
	public Texture img;
	public int amount = 0;
	
	private final Color textColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private final Color tintColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	private BobEffect bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
	protected float fontScale = 0.4F; //0.7
	
	public OrbIntent(AbstractMonster target, int amount) {
		this.target = target;
		this.amount = amount;
		this.img = TextureLoader.getTexture(makeOrbIntentPath(GetIntentResource()));
	}
	
	public void ReloadImage() {
		this.img = TextureLoader.getTexture(makeOrbIntentPath(GetIntentResource()));
	}
	
	public void render(SpriteBatch sb, float xOff, float yOff) {
		bobEffect.update();
		float x = target.intentHb.cX - 64.0f;
		float y = target.intentHb.cY - 72.0f + (bobEffect.y / 2.0f);
		float angle = 0f;
		sb.setColor(tintColor);
        sb.draw(this.img, x, y, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale * 1.2F, Settings.scale * 1.2F, angle, 0, 0, 128, 128, false, false);
        FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amount), x + 15, y + 88.0f, textColor, this.fontScale);
        
		/*
		sb.draw(img, x, y);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amount), x + 30, y, textColor, this.fontScale);*/

	}
	
	public abstract String GetIntentResource();

	public boolean amountBetweenRanges(int min, int max) {
		return amount >= min && amount <= max;
	}
	
}
