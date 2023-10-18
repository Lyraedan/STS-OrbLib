package orbLib.actions;

import java.util.HashMap;
import java.util.Set;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class ChannelOrbListenerAction extends AbstractGameAction {

	private String className;
	
	public ChannelOrbListenerAction(String className) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.className = className;
	}
	
	@Override
	public void update() {		
		// Find any orbs listening to this orb and trigger their listeners
		/**
		 * Key <INVOKER>_<LISTENER>_<TYPE>
		 * Value ListenerAction
		 * */
		HashMap<String, OrbListenerAction> orbListener = OrbLib.orbListener.queue.get(className);
		if(orbListener == null) {
			this.isDone = true;
			return;
		}
		
		Set<String> keys = orbListener.keySet();
		if(keys == null) {
			this.isDone = true;
			return;
		}
		
		for(String invoker : keys) {
			OrbListenerAction action = orbListener.get(invoker);
			if(action == null) {
				this.isDone = true;
				return;
			}
			if(action.orbToListenFor.equals(className) && action.type.equals(OrbListenerType.CHANNELLED)) {
				System.out.println(className + " was channelled!");
				action.action.Invoke(className);
			}
		}
		
		this.isDone = true;
	}

}
