package net.intelie.challenges.domain.service.impl;
import static net.intelie.challenges.domain.infrastructure.utils.EventUtils.nowToMillis;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.intelie.challenges.domain.model.Event;

@Service
@RequiredArgsConstructor
/**
 * Provides a CRUD of event records on redis db
 * 
 * @author franciscogarrigo
 *
 */
public class RedisService {

	private final RedisTemplate<String, Event> redisTemplate;

	private final static String KEY_PREFIX = "EventStore-";	
	
	/**
	 *  Find event records by type and an especified range of timestamp
	 * 
	 * @param type
	 * @param startTime
	 * @param endTime
	 * 
	 */
	public Set<Event> query(String type, long startTime, long endTime) {
		
		return redisTemplate.opsForZSet().rangeByScore(KEY_PREFIX+type, startTime, endTime);
		
	}
	
	/**
	 * Write a record to redis. The method use Zset to harness its way to store a group of records based on a score. In this case, the score is the timestamp
	 * of the insertion of record 
	 * 
	 * @param event
	 * 
	 */
	public void put(Event event) {
		redisTemplate.opsForZSet().add(KEY_PREFIX+event.getType(), event, event.getTimestamp()); 
	}
	
	/**
	 * 
	 * Clear all event records by a type. 
	 * 
	 * As the way of save needs a score (timestamp), then, when delete call occurs, 
	 * to ensure complete deletion, the score is the range between 0 and timestamp corresponding to now 
	 * 
	 * @param type
	 * 
	 */

	public void clean(String type) {
		redisTemplate.opsForZSet().removeRange(KEY_PREFIX+type,0,nowToMillis());
	}
	
	
	
	

}
