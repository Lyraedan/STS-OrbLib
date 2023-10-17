package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.MeteorStrike;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectPlasmaOrb;

@SpirePatch(clz = MeteorStrike.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectMeteorStrikePatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(MeteorStrike __instance, AbstractPlayer p,
			AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;

		if (m != null)
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
			AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8F));
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m,
				new DamageInfo((AbstractCreature) p, __instance.damage, __instance.damageTypeForTurn),
				AbstractGameAction.AttackEffect.NONE));
		for (int i = 0; i < __instance.magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectPlasmaOrb(), !isDefect));
		}

		return SpireReturn.Return();
	}
}
