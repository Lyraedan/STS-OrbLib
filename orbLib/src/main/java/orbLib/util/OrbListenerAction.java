package orbLib.util;

public class OrbListenerAction {
	
	public enum OrbListenerType {
		CHANNELLED, EVOKED, REMOVED, EVOKED_OR_REMOVED, RIGHT_CLICKED
	}
	
	public String orbToListenFor = "None";
	public OrbAction action;
	public OrbListenerType type = OrbListenerType.EVOKED;
}
