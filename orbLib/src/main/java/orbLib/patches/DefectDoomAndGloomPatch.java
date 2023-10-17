package orbLib.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.blue.DoomAndGloom;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import orbLib.actions.ExtendedChannelAction;
import orbLib.orbs.DefectDarkOrb;

@SpirePatch(clz = DoomAndGloom.class, method = "use", paramtypez = { AbstractPlayer.class, AbstractMonster.class, })
public class DefectDoomAndGloomPatch {
	@SpirePrefixPatch
	public static SpireReturn<Void> ReplaceWithUpdatedOrb(DoomAndGloom __instance, AbstractPlayer p, AbstractMonster m) {
		boolean isDefect = AbstractDungeon.player instanceof com.megacrit.cardcrawl.characters.Defect;
		
		AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
		AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1F));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, __instance.multiDamage, __instance.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		AbstractDungeon.actionManager.addToBottom(new ExtendedChannelAction(new DefectDarkOrb(), !isDefect));
		
		return SpireReturn.Return();
	}
}
