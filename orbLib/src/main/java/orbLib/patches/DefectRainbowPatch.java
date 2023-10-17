package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectDarkOrb;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = Zap.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectRainbowPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Zap __instance, AbstractPlayer p, AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
			
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainbowCardEffect()));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectLightningOrb(), !isDefect));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), !isDefect));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectDarkOrb(), !isDefect));	
	    
		return SpireReturn.Return();
	}
}
