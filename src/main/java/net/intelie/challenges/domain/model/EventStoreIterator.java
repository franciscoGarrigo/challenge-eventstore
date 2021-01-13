package net.intelie.challenges.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor

/**
 * Implementation of an iterator
 * 
 * {@link #moveNext} starts incrementing the variable index to adjust the position for the first element (index 0)
 * 
 * the index begins in -1 for the first call of {@link #moveNext} Method
 * 
 * the variable <b>called</b> is a boolean that ensures the mandatory call to {@link #moveNext} to guarantee the integrity of a iterator and its list
 * 
 * the method {@link #close} clear the aux list when the iterator executions comes to the end
 * 
 * @throws IllegalStateException when methods {@link #current} and {@link #remove} are called without previous call of moveNext or when the list came to the end
 * 
 * @author franciscogarrigo
 *
 */
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
		if (Objects.nonNull(events)) {
			this.events.addAll(events);
		}
	}

}
