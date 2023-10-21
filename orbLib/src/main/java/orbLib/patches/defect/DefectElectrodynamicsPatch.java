package orbLib.patches.defect;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.blue.Electrodynamics;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ElectroPower;

import orbLib.OrbLib;
import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = Electrodynamics.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectElectrodynamicsPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(Electrodynamics __instance, AbstractPlayer p,
			AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		if (!p.hasPower("Electrodynamics"))
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(p, p, new ElectroPower((AbstractCreature) p)));
		for (int i = 0; i < __instance.magicNumber; i++) {
			DefectLightningOrb lightning = new DefectLightningOrb();
			AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(lightning, OrbLib.CONFIG_DEFECT_EVOKE_ALL_ORBS_ON_FULL));
		}

		return SpireReturn.Return();
	}
}
