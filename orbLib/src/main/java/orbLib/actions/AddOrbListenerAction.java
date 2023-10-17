package orbLib.actions;

import java.util.HashMap;
import java.util.stream.IntStream;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.OrbLib;
import orbLib.orbs.ExtendedOrb;
import orbLib.util.OrbAction;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbLibUtils;

public class AddOrbListenerAction extends AbstractGameAction {

	public String invokerClassName;
	public String className;
	public OrbAction action;

	public AddOrbListenerAction(String invokerClassName, String className, OrbAction action) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.invokerClassName = invokerClassName;
		this.className = className;
		this.action = action;
	}

	@Override
	public void update() {
		if (OrbLib.orbListener.queue.containsKey(className)) {
			HashMap<String, OrbListenerAction> queueMap = OrbLib.orbListener.queue.get(className);
			if(queueMap.containsKey(invokerClassName)) {
				this.isDone = true;
				return;
			}
			System.out.println(className + " already has listeners, adding to listener queue from " + invokerClassName);
			OrbListenerAction listener = new OrbListenerAction();
			listener.orbToListenFor = this.className;
			listener.action = action;
			queueMap.put(invokerClassName, listener);
			this.isDone = true;
			return;
		}
		OrbListenerAction listener = new OrbListenerAction();
		listener.orbToListenFor = this.className;
		listener.action = action;
		System.out.println("Adding listener for " + this.className);
		
		HashMap<String, OrbListenerAction> map = new HashMap<String, OrbListenerAction>();
		map.put(invokerClassName, listener);
		OrbLib.orbListener.queue.put(this.className, map);
		this.isDone = true;
	}

}
