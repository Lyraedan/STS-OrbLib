package orbLib.patches.defect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.blue.Tempest;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import orbLib.OrbLib;
import orbLib.actions.TempestPatchedAction;

@SpirePatch(clz = Tempest.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectTempestPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Tempest __instance, AbstractPlayer p, AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
				
		AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TempestPatchedAction(p, __instance.energyOnUse, __instance.upgraded, __instance.freeToPlayOnce, OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		
		return SpireReturn.Return();
	}
}
