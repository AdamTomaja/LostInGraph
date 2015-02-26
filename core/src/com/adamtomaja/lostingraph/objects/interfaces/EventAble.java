package com.adamtomaja.lostingraph.objects.interfaces;

public interface EventAble {
	public void addEventListener(InteractiveEventListener interactiveEventListener);
	public void generateEvent(TEventType type);
}
