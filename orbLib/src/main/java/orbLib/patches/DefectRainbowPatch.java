package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.blue.Rainbow;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.RainbowCardEffect;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectDarkOrb;
import orbLib.orbs.DefectFrostOrb;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = Rainbow.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectRainbowPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Rainbow __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
					
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new RainbowCardEffect()));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectLightningOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectDarkOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));	
	    
		return SpireReturn.Return();
	}
}
