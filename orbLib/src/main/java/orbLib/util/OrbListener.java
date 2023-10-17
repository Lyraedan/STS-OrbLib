package orbLib.util;

import java.util.HashMap;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.actions.AddOrbListenerAction;
import orbLib.actions.EvokeOrbListenerAction;
import orbLib.actions.RemoveOrbListenerAction;

public class OrbListener {
	
	/**
	 * Queue key is listener orb
	 * Queue value is the map of invoker listeners
	 * Queue element key is invoker orb
	 * Queue element value is the action
	 * */
	public HashMap<String, HashMap<String, OrbListenerAction>> queue = new HashMap<String, HashMap<String, OrbListenerAction>>();
	
	public void AddListener(String invokerClassName, String listenerClassName, OrbAction action) {
		AbstractDungeon.actionManager.addToTop(new AddOrbListenerAction(invokerClassName, listenerClassName, action));
	}

	public void RemoveListener(String className) {
		AbstractDungeon.actionManager.addToTop(new RemoveOrbListenerAction(className));
	}

	public void OnOrbEvoked(String listenerClassName) {
		AbstractDungeon.actionManager.addToTop(new EvokeOrbListenerAction(listenerClassName));
	}
	
}
