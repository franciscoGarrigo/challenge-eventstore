package net.intelie.challenges.domain.service.impl;
import static net.intelie.challenges.domain.infrastructure.utils.EventUtils.toMillis;

import java.util.Set;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import net.intelie.challenges.domain.model.Event;

@Service
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate<String, Event> redisTemplate;

	private final static String KEY_PREFIX = "EventStore-";
	
	
	public void put(Event value) {
		redisTemplate.opsForZSet().add(KEY_PREFIX+value.getType(), value, value.getTimestamp());
	}

	public void clean(String type) {
		redisTemplate.opsForZSet().removeRange(KEY_PREFIX+type,0,toMillis());
	}
	
	public Set<Event> query(String type, long startTime, long endTime) {
		
		return redisTemplate.opsForZSet().rangeByScore(KEY_PREFIX+type, startTime, endTime);
		
	}

}
