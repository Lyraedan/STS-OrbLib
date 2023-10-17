package orbLib.actions;

import java.util.HashMap;
import java.util.Set;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbListenerAction;

public class EvokeOrbListenerAction extends AbstractGameAction {

	private String className;
	
	public EvokeOrbListenerAction(String className) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.className = className;
	}
	
	@Override
	public void update() {		
		// Find any orbs listening to this orb and trigger their listeners
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
			if(action.orbToListenFor.equals(className)) {
				action.action.Invoke(className);
			}
		}
		
		this.isDone = true;
	}

}
