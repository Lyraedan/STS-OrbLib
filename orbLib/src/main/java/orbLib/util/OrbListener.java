package orbLib.util;

import java.util.HashMap;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.actions.AddOrbListenerAction;
import orbLib.actions.ChannelOrbListenerAction;
import orbLib.actions.EvokeOrbListenerAction;
import orbLib.actions.RemoveOrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class OrbListener {
	
	/**
	 * Queue key is listener orb
	 * Queue value is the map of invoker listeners
	 * Queue element key is invoker orb
	 * Queue element value is the action
	 * */
	public HashMap<String, HashMap<String, OrbListenerAction>> queue = new HashMap<String, HashMap<String, OrbListenerAction>>();
	
	public void AddListener(String invokerClassName, String listenerClassName, OrbListenerType listenerType, OrbAction action) {
		AbstractDungeon.actionManager.addToTop(new AddOrbListenerAction(invokerClassName, listenerClassName, listenerType, action));
	}

	public void RemoveListener(String className, OrbListenerType type) {
		AbstractDungeon.actionManager.addToTop(new RemoveOrbListenerAction(className, type));
	}
	
	public void RemoveAllListeners(String className) {
		RemoveListener(className, OrbListenerType.CHANNELLED);
		RemoveListener(className, OrbListenerType.EVOKED);
	}

	public void OnOrbEvoked(String listenerClassName) {
		AbstractDungeon.actionManager.addToTop(new EvokeOrbListenerAction(listenerClassName));
	}
	
	public void OnOrbChannelled(String listenerClassName) {
		AbstractDungeon.actionManager.addToTop(new ChannelOrbListenerAction(listenerClassName));
	}
	
}
