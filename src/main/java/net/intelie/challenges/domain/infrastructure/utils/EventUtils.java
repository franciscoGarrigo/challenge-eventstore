package net.intelie.challenges.domain.infrastructure.utils;

import static net.intelie.challenges.domain.model.EventDTO.toEventDTO;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.experimental.UtilityClass;
import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventDTO;
import net.intelie.challenges.domain.model.EventIterator;
import net.intelie.challenges.domain.model.EventType;
import net.intelie.challenges.domain.model.OriginType;
import net.intelie.challenges.domain.model.Payload;

@UtilityClass
public class EventUtils {

	public static Event createEvent(Payload payload) {		
		
		LocalDateTime now = LocalDateTime.now();
				
		return Event.builder()
				.id(UUID.nameUUIDFromBytes((payload+now.toString()).getBytes()).toString())
				.type(EventType.valueOf(payload.getType()))
				.origin(OriginType.valueOf(payload.getOrigin()))
				.timestamp(toMillis(now))
				.build();
		
	}
	
	public static long toMillis(LocalDateTime time) {
		return time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
	}
	
	public static long toMillis() {
		return toMillis(LocalDateTime.now());
	}
	
	public static List<EventDTO> getListFromIterator(EventIterator iterator){
		
		List<EventDTO> result = new ArrayList<>();
		
		while (iterator.moveNext()) {
			result.add(toEventDTO(iterator.current()));
		}
		
		return result;
	}
	
	
}
