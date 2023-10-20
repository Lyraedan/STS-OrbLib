package orbLib.actions;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.Settings;

import orbLib.OrbLib;
import orbLib.util.OrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class BulkRemoveAllOfListenerTypeAction extends AbstractGameAction {

	public String className;
	public OrbListenerType type;
	
	public BulkRemoveAllOfListenerTypeAction(String className, OrbListenerType type) {
		amount = 1;
		actionType = ActionType.SPECIAL;
		duration = Settings.ACTION_DUR_FAST;
		this.className = className;
		this.type = type;
	}

	@Override
	public void update() {
		Set<String> keys = OrbLib.orbListener.queue.keySet();
		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();
			HashMap<String, OrbListenerAction> mapped = OrbLib.orbListener.queue.get(key);
			Set<String> nestedKeys = mapped.keySet();
			Iterator<String> nestedIterator = nestedKeys.iterator();
			while (nestedIterator.hasNext()) {
				String nestedKey = nestedIterator.next();
				if(nestedKey.startsWith(className) && nestedKey.endsWith(type.toString())) {
					nestedIterator.remove();
				}
			}
			if(mapped.isEmpty()) {
				iterator.remove();
			}
		}
		
		this.isDone = true;
	}

}
