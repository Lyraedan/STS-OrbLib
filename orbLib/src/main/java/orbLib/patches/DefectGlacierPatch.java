package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.blue.Glacier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectFrostOrb;

@SpirePatch(clz = Glacier.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectGlacierPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Glacier __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
				
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, __instance.block));
	    for (int i = 0; i < __instance.magicNumber; i++)
	    	AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ExtendedChannelAction(new DefectFrostOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL)); 
		
		return SpireReturn.Return();
	}
}
