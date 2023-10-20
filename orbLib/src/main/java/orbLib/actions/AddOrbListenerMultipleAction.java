package orbLib.actions;

import java.util.ArrayList;
import java.util.HashMap;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbAction;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class AddOrbListenerMultipleAction extends AbstractGameAction {

	public String invokerClassName;
	public ArrayList<Class<?>> listeners;
	public OrbListenerType type;
	public OrbAction action;

	public AddOrbListenerMultipleAction(String invokerClassName, ArrayList<Class<?>> listeners, OrbListenerType type,
			OrbAction action) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.invokerClassName = invokerClassName;
		this.listeners = listeners;
		this.type = type;
		this.action = action;
	}

	@Override
	public void update() {
		for (int i = 0; i < listeners.size(); i++) {
			String listenerClassName = listeners.get(i).getSimpleName();
			String key = invokerClassName + "_" + listenerClassName + "_" + this.type.toString();
			if (OrbLib.orbListener.queue.containsKey(listenerClassName)) {
				HashMap<String, OrbListenerAction> queueMap = OrbLib.orbListener.queue.get(listenerClassName);
				if (queueMap.containsKey(key)) {
					this.isDone = true;
					return;
				}
				OrbListenerAction listener = new OrbListenerAction();
				listener.orbToListenFor = listenerClassName;
				listener.action = action;
				listener.type = type;

				queueMap.put(key, listener);
				this.isDone = true;
				return;
			}
			OrbListenerAction listener = new OrbListenerAction();
			listener.orbToListenFor = listenerClassName;
			listener.type = this.type;
			listener.action = this.action;

			HashMap<String, OrbListenerAction> map = new HashMap<String, OrbListenerAction>();
			map.put(key, listener);
			OrbLib.orbListener.queue.put(listenerClassName, map);
		}
		this.isDone = true;
	}

}
