package net.intelie.challenges.domain.infrastructure.utils;

import static net.intelie.challenges.domain.model.EventDTO.toEventDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.ObjectUtils;

import lombok.experimental.UtilityClass;
import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventDTO;
import net.intelie.challenges.domain.model.EventIterator;
import net.intelie.challenges.domain.model.EventType;
import net.intelie.challenges.domain.model.OriginType;
import net.intelie.challenges.domain.model.Payload;

@UtilityClass
public class EventUtils {
	
	public static final String DEFAULT_EVENT_GENERATOR_NAME = "Very-Crazy-Generator-Service";

	/*
	 * Method that convert an valid external payload in to a recordable event
	 *  
	 */
	public static Event createEvent(Payload payload) {		
		
		Long nowMillis = nowToMillis();
				
		return Event.builder()
				.id(UUID.nameUUIDFromBytes((payload.toString()+nowMillis.toString()).getBytes()).toString())
				.eventGenerator(ObjectUtils.defaultIfNull(payload.getEventGenerator(), DEFAULT_EVENT_GENERATOR_NAME))
				.type(EventType.valueOf(payload.getType()))
				.origin(OriginType.valueOf(payload.getOrigin()))
				.timestamp(nowMillis)
				.build();
		
	}
	
	public static long nowToMillis() {
		return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static List<EventDTO> getListFromIterator(EventIterator iterator) throws Exception{
		
		List<EventDTO> result = new ArrayList<>();
		
		try(iterator){
			while (iterator.moveNext()) {
				result.add(toEventDTO(iterator.current()));
			}
		}
		
		return result;
	}
	
	
}
