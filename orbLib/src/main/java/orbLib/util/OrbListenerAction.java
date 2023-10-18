package orbLib.util;

public class OrbListenerAction {
	
	public enum OrbListenerType {
		CHANNELLED, EVOKED
	}
	
	public String orbToListenFor = "None";
	public OrbAction action;
	public OrbListenerType type = OrbListenerType.EVOKED;
}
