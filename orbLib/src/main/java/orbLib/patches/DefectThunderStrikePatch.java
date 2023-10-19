package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.defect.NewThunderStrikeAction;
import com.megacrit.cardcrawl.cards.blue.ThunderStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;

import orbLib.OrbLib;
import orbLib.orbs.DefectLightningOrb;

@SpirePatch(clz = ThunderStrike.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectThunderStrikePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(ThunderStrike __instance, AbstractPlayer p,
			AbstractMonster m) {
		if(!OrbLib.CONFIG_PATCH_DEFECT) {
			return SpireReturn.Continue();
		}
		
		__instance.baseMagicNumber = 0;
		for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
			if (o instanceof DefectLightningOrb)
				__instance.baseMagicNumber++;
		}
		__instance.magicNumber = __instance.baseMagicNumber;
		for (int i = 0; i < __instance.magicNumber; i++)
			AbstractDungeon.actionManager.addToBottom(new NewThunderStrikeAction(__instance));

		return SpireReturn.Return();
	}
}

@SpirePatch(clz = ThunderStrike.class, method = "applyPowers")
class DefectThunderStrikeApplyPowersPatch {

	@SpireInsertPatch(rloc = 7)
	public static void Insert(ThunderStrike __instance) {
		for (AbstractOrb o : AbstractDungeon.actionManager.orbsChanneledThisCombat) {
			if (o instanceof DefectLightningOrb)
				__instance.baseMagicNumber++;
		}
	}
}
