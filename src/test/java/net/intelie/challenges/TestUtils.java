package net.intelie.challenges;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventType;
import net.intelie.challenges.domain.model.OriginType;
import net.intelie.challenges.domain.model.Payload;

public class TestUtils {

	
	private static final String EVENT_GENERATOR = "abc123";
	private static final String ID = "9f25b8b3-24f7-43f1-8635-a2006d01ddc0";

	public static Set<Event> createSetOfEvents(){
		Set<Event> set = new HashSet<>();
		
		set.add(Event.builder()
				.eventGenerator(EVENT_GENERATOR)
				.id(ID)
				.origin(OriginType.PIPELINE)
				.type(EventType.BATCH)
				.timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
				.build());
		
		return set;	
		
	}
	
	public static Payload createPayload() {
		
		return Payload.builder().eventGenerator(EVENT_GENERATOR)
					.origin("PIPELINE")
					.type("BATCH")
					.build();
	}
	
	public static Payload createInvalidPayload() {
		
		return Payload.builder().eventGenerator(EVENT_GENERATOR)
					.origin("BLU_SCREEN_OF_DEATH")
					.type("POST")
					.build();
	}
}
