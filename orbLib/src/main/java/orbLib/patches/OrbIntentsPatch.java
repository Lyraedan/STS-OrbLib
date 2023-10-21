package orbLib.patches;

import java.util.ArrayList;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.AbstractCreature;

import orbLib.orbs.intents.OrbIntent;

@SpirePatch(
        clz=AbstractCreature.class,
        method=SpirePatch.CLASS
)
public class OrbIntentsPatch
{
    public static SpireField<ArrayList<OrbIntent>> orbIntents = new SpireField<ArrayList<OrbIntent>>(() -> new ArrayList<OrbIntent>());
}