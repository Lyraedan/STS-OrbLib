package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.blue.Chill;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectFrostOrb;

@SpirePatch(clz = Chill.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectChillPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Chill __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
				
		int count = 0;
	    for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
	      if (!mon.isDeadOrEscaped())
	        count++; 
	    } 
	    for (int i = 0; i < count * __instance.magicNumber; i++)
	      AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectFrostOrb(), OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL)); 
		
		return SpireReturn.Return();
	}
}
