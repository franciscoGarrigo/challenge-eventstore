package net.intelie.challenges.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class EventStoreIterator implements EventIterator{
	
	private List<Event> events;
	
	private int index = -1;
	
	private boolean next;
	
	private boolean called;
	
	@Override
	public void close() throws Exception {
		events.clear();
	}

	@Override
	public boolean moveNext() {
		index++;
		next = index < events.size();
		called = true;
		return next;
	}

	@Override
	public Event current() {
		if (!called){
			throw new IllegalStateException("MoveNext not invoked");
		}
		
		if (!next){
			throw new IllegalStateException("End of iterator");
		}
		
		return events.get(index);
	}

	@Override
	public void remove() {
		if (!called){
			throw new IllegalStateException("MoveNext not invoked");
		}
		
		if (!next){
			throw new IllegalStateException("End of iterator");
		}
		events.remove(index);
		
	}
	
	public void setEvents(Set<Event> events) {
		this.events = new ArrayList<>();
		this.events.addAll(events);
	}

}
