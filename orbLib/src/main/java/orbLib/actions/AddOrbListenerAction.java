package orbLib.actions;

import java.util.HashMap;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbAction;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class AddOrbListenerAction extends AbstractGameAction {

	public String invokerClassName;
	public String listenerClassName;
	public OrbListenerType type;
	public OrbAction action;

	public AddOrbListenerAction(String invokerClassName, String listenerClassName, OrbListenerType type, OrbAction action) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.invokerClassName = invokerClassName;
		this.listenerClassName = listenerClassName;
		this.type = type;
		this.action = action;
	}

	@Override
	public void update() {
		String key = invokerClassName + "_" + this.listenerClassName + "_" + this.type.toString();
		if (OrbLib.orbListener.queue.containsKey(this.listenerClassName)) {
			HashMap<String, OrbListenerAction> queueMap = OrbLib.orbListener.queue.get(this.listenerClassName);
			if(queueMap.containsKey(key)) {
				this.isDone = true;
				return;
			}
			OrbListenerAction listener = new OrbListenerAction();
			listener.orbToListenFor = this.listenerClassName;
			listener.action = action;
			listener.type = type;
			
			queueMap.put(key, listener);
			this.isDone = true;
			return;
		}
		OrbListenerAction listener = new OrbListenerAction();
		listener.orbToListenFor = this.listenerClassName;
		listener.type = this.type;
		listener.action = this.action;
		
		HashMap<String, OrbListenerAction> map = new HashMap<String, OrbListenerAction>();
		map.put(key, listener);
		OrbLib.orbListener.queue.put(this.listenerClassName, map);
		this.isDone = true;
	}

}
