package orbLib.orbs.intents;

import static orbLib.OrbLib.makeOrbIntentPath;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.BobEffect;

import orbLib.OrbLib;
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

	public PowerTip tip;
	
	public OrbIntent(AbstractCreature target, int amount, Intents intent) {
		this.target = target;
		this.amount = amount;
		this.intent = intent;
		ReloadImage();
		tip = new PowerTip("Orb Intent: " + getName(), getDescription(), this.img);
	}

	public void ReloadImage() {
		this.img = TextureLoader.getTexture(makeOrbIntentPath(GetIntentResource()));
		tip = new PowerTip("Orb Intent: " + getName(), getDescription(), this.img);
	}

	public void render(SpriteBatch sb, float x, float y) {
		if (!OrbLib.CONFIG_DISPLAY_ORB_INTENTS) {
			return;
		}

		if (AbstractDungeon.player.isDead) {
			return;
		}
		
		if(target instanceof AbstractMonster) {
			if(((AbstractMonster)target).isDead) {
				return;
			}
		}

		bobEffect.update();
		sb.setColor(tintColor);

		float scale = target instanceof AbstractMonster ? Settings.scale * 1.2f : Settings.scale;
		sb.draw(this.img, x - 20f, (y + (bobEffect.y / 2.0f)) - 20f, 32.0F, 32.0F, 64.0F, 64.0F, scale, scale, 0.0F, 0,
				0, 64, 64, false, false);
		FontHelper.renderFontCentered(sb, FontHelper.cardEnergyFont_L, Integer.toString(amount), x,
				y + (bobEffect.y / 2.0f), textColor, this.fontScale);
	}

	public void render(SpriteBatch sb, float x, float y, float xOff, float yOff) {
		sb.draw(this.img, (x + xOff) + -46.0F * Settings.scale - 32.0F, (y * yOff) + -14.0F * Settings.scale - 32.0F,
				32.0F, 32.0F, 64.0F, 64.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
		FontHelper.renderFontCentered(sb, FontHelper.blockInfoFont, Integer.toString(amount),
				x + -46.0F * Settings.scale, y - 16.0F * Settings.scale, new Color(0.9F, 0.9F, 0.9F, 0.9F), 1.0F);
	}

	public abstract String GetIntentResource();

	public boolean amountBetweenRanges(int min, int max) {
		return amount >= min && amount <= max;
	}

	public abstract OrbIntent makeCopy();
	
	public String getName() {
		String lowerCaseString = intent.toString().toLowerCase();

		char firstChar = lowerCaseString.charAt(0);
		char upperCaseFirstChar = Character.toUpperCase(firstChar);

		String result = upperCaseFirstChar + lowerCaseString.substring(1);
		return result;
	}

	public String getDescription() {
		switch (intent) {
			case AGGRESSIVE:
				return "Your orbs intend to attack this creature";
			case DEFENSIVE:
				return "Your orbs intend to defend this creature.";
			case DEBUFF:
				return "Your orbs intend to apply a debuff to this creature.";
			case SLEEPING:
				return "Your orbs intend to put this creature to sleep.";
			case STUNNED:
				return "Your orbs intend to stun this creature.";
			case UNKNOWN:
				return "Your orbs intend to apply an unknown effect to this creature.";
			default:
				return "Your orbs intend to apply an unknown effect to this creature.";
		}
	}

}
