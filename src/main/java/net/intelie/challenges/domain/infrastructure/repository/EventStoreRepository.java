package net.intelie.challenges.domain.infrastructure.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.intelie.challenges.domain.model.Event;

public interface EventStoreRepository extends CrudRepository<Event, String>{
	
	public void deleteAllByType(String type);

	public List<Event> findTypeAndTimestampBetweenStartTimeAndEndTime(String type, long startTime, long endTime);

}
