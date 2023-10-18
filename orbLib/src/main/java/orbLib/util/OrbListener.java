package orbLib.util;

import java.util.HashMap;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.actions.AddOrbListenerAction;
import orbLib.actions.RemoveOrbListenerAction;
import orbLib.actions.TriggerOrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class OrbListener {
	
	/**
	 * Queue key is listener orb
	 * Queue value is the map of invoker listeners
	 * Queue element key is invoker orb
	 * Queue element value is the action
	 * */
	public HashMap<String, HashMap<String, OrbListenerAction>> queue = new HashMap<String, HashMap<String, OrbListenerAction>>();
	
	public void AddListener(Class<?> listener, Class<?> listenFor, OrbListenerType listenerType, OrbAction action) {
		AbstractDungeon.actionManager.addToTop(new AddOrbListenerAction(listener.getSimpleName(), listenFor.getSimpleName(), listenerType, action));
	}

	public void RemoveListener(Class<?> listener, OrbListenerType type) {
		AbstractDungeon.actionManager.addToTop(new RemoveOrbListenerAction(listener.getSimpleName(), type));
	}
	
	public void RemoveAllListeners(Class<?> listener) {
		RemoveListener(listener, OrbListenerType.CHANNELLED);
		RemoveListener(listener, OrbListenerType.EVOKED);
		RemoveListener(listener, OrbListenerType.REMOVED);
		RemoveListener(listener, OrbListenerType.EVOKED_OR_REMOVED);
		RemoveListener(listener, OrbListenerType.RIGHT_CLICKED);
	}

	public void OnOrbEvoked(Class<?> listener) {
		AbstractDungeon.actionManager.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.EVOKED));
		OnOrbEvokedOrRemoved(listener);
	}
	
	public void OnOrbChannelled(Class<?> listener) {
		AbstractDungeon.actionManager.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.CHANNELLED));
	}
	
	public void OnOrbRemoved(Class<?> listener) {
		AbstractDungeon.actionManager.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.REMOVED));
		OnOrbEvokedOrRemoved(listener);
	}
	
	public void OnOrbEvokedOrRemoved(Class<?> listener) {
		AbstractDungeon.actionManager.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.EVOKED_OR_REMOVED));
	}
	
	public void OnOrbRightClicked(Class<?> listener) {
		AbstractDungeon.actionManager.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.RIGHT_CLICKED));
	}
	
}
