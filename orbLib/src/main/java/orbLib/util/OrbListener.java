package orbLib.util;

import java.util.ArrayList;
import java.util.HashMap;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import orbLib.actions.AddOrbListenerAction;
import orbLib.actions.AddOrbListenerMultipleAction;
import orbLib.actions.BulkRemoveAllAction;
import orbLib.actions.BulkRemoveAllOfListenerTypeAction;
import orbLib.actions.RemoveOrbListenerAction;
import orbLib.actions.TriggerOrbListenerAction;
import orbLib.util.OrbListenerAction.OrbListenerType;

public class OrbListener {

	/**
	 * Queue key is listener orb Queue value is the map of invoker listeners Queue
	 * element key is invoker orb Queue element value is the action
	 */
	public HashMap<String, HashMap<String, OrbListenerAction>> queue = new HashMap<String, HashMap<String, OrbListenerAction>>();

	/**
	 * <summary>
	 * Make the listener listen out for events from the listenFor orb
	 * </summary>
	 * **/
	public void AddListener(Class<?> listener, Class<?> listenFor, OrbListenerType listenerType, OrbAction action) {
		AbstractDungeon.actionManager.addToTop(
				new AddOrbListenerAction(listener.getSimpleName(), listenFor.getSimpleName(), listenerType, action));
	}
	
	/**
	 * <summary>
	 * Make the listener listen out for events from the multiple orbs
	 * </summary>
	 * **/
	public void AddListenerMultiple(Class<?> listener, ArrayList<Class<?>> listenForList, OrbListenerType listenerType, OrbAction action) {
		AbstractDungeon.actionManager.addToTop(
				new AddOrbListenerMultipleAction(listener.getSimpleName(), listenForList, listenerType, action));
	}

	/**
	 * <summary>
	 * Remove a specific listener off an orb of a specific type.
	 * </summary>
	 * **/
	public void RemoveListener(Class<?> listener, OrbListenerType type) {
		AbstractDungeon.actionManager.addToTop(new RemoveOrbListenerAction(listener.getSimpleName(), type));
	}
	
	/**
	 * <summary>
	 * Remove all listeners off the specified orb
	 * </summary>
	 * **/
	public void RemoveAllListeners(Class<?> classOrb) {
		AbstractDungeon.actionManager.addToTop(new BulkRemoveAllAction(classOrb.getSimpleName()));
	}
	
	/**
	 * <summary>
	 * Remove all listeners off the specified orb of a specific type
	 * </summary>
	 * **/
	public void RemoveAllListenersOfType(Class<?> classOrb, OrbListenerType typeToRemove) {
		AbstractDungeon.actionManager.addToTop(new BulkRemoveAllOfListenerTypeAction(classOrb.getSimpleName(), typeToRemove));
	}

	/**
	 * <summary>
	 * LEAVE THIS FUNCTION ALONE
	 * </summary>
	 * **/
	public void OnOrbEvoked(Class<?> listener) {
		AbstractDungeon.actionManager
				.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.EVOKED));
		OnOrbEvokedOrRemoved(listener);
	}

	/**
	 * <summary>
	 * LEAVE THIS FUNCTION ALONE
	 * </summary>
	 * **/
	public void OnOrbChannelled(Class<?> listener) {
		AbstractDungeon.actionManager
				.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.CHANNELLED));
	}

	/**
	 * <summary>
	 * LEAVE THIS FUNCTION ALONE
	 * </summary>
	 * **/
	public void OnOrbRemoved(Class<?> listener) {
		AbstractDungeon.actionManager
				.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.REMOVED));
		OnOrbEvokedOrRemoved(listener);
	}

	/**
	 * <summary>
	 * LEAVE THIS FUNCTION ALONE
	 * </summary>
	 * **/
	public void OnOrbEvokedOrRemoved(Class<?> listener) {
		AbstractDungeon.actionManager
				.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.EVOKED_OR_REMOVED));
	}

	/**
	 * <summary>
	 * LEAVE THIS FUNCTION ALONE
	 * </summary>
	 * **/
	public void OnOrbRightClicked(Class<?> listener) {
		AbstractDungeon.actionManager
				.addToTop(new TriggerOrbListenerAction(listener.getSimpleName(), OrbListenerType.RIGHT_CLICKED));
	}

}
