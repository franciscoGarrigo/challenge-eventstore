package net.intelie.challenges.domain.service.impl;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.intelie.challenges.domain.infrastructure.repository.EventStoreRepository;
import net.intelie.challenges.domain.infrastructure.utils.CustomStopWatch;
import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventIterator;
import net.intelie.challenges.domain.model.EventStoreIterator;
import net.intelie.challenges.domain.service.EventStore;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventStoreService implements EventStore{
	
	public final RedisService redisService;
	
	public final EventStoreRepository repository;
	
	@Override
	public void insert(Event event) {
		@Cleanup("stop")
        StopWatch stopWatch = new CustomStopWatch();
		
		redisService.put(event);
		
		log.info("Event {} successful created. Duration {} ms",event,stopWatch.getTotalTimeMillis());
		
	}

	@Override
	public void removeAll(String type) {
		redisService.clean(type);		
	}

	@Override
	public EventIterator query(String type, long startTime, long endTime) {
		@Cleanup("stop")
        StopWatch stopWatch = new CustomStopWatch();
		
		Set<Event> events = redisService.query(type, startTime, endTime);
		
		EventStoreIterator iterator = new EventStoreIterator();
		iterator.setEvents(events);	
		
		log.info("Query got {} events. {}. Duration {} ms",events.size(), events, stopWatch.getTotalTimeMillis());
		
		return iterator;
		
	}

}
