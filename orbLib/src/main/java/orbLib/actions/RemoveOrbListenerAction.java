package orbLib.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class RemoveOrbListenerAction extends AbstractGameAction {

	public String className;
	public OrbListenerType type;
	
	public RemoveOrbListenerAction(String className, OrbListenerType type) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.className = className;
		this.type = type;
	}

	@Override
	public void update() {
		if (!OrbLib.orbListener.queue.containsKey(this.className)) {
			this.isDone = true;
			return;
		}
		System.out.println("Removing a listener for " + this.className);
		
		HashMap<String, OrbListenerAction> orbListener = OrbLib.orbListener.queue.get(this.className);
		if(orbListener == null) {
			this.isDone = true;
			return;
		}

		Set<String> keys = orbListener.keySet();
		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
		    String invoker = iterator.next();
		    OrbListenerAction action = orbListener.get(invoker);

		    if (action == null) {
		        this.isDone = true;
		        return;
		    }

		    if (action.orbToListenFor.equals(this.className) && action.type.equals(type)) {
		        System.out.println("Removed nested listener: " + invoker);
		        iterator.remove();
		    }
		}
		
		// There are no more listeners remove the root
		if(orbListener.isEmpty()) {
			System.out.println("No more nested listeners removing " + this.className + " from queue");
			OrbLib.orbListener.queue.remove(this.className); // Remove the orb listener
		}
		
		this.isDone = true;
	}

}
