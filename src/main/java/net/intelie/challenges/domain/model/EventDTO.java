package net.intelie.challenges.domain.model;
import static net.intelie.challenges.domain.infrastructure.utils.EventUtils.DEFAULT_EVENT_GENERATOR_NAME;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.apache.commons.lang3.ObjectUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {	
	
	private EventType type;

	private OriginType origin;
	
	private String eventGenerator;
	
    private LocalDateTime eventDate;
    
    public static EventDTO toEventDTO(Event event) {
    	
    	return EventDTO.builder()
				.eventGenerator(ObjectUtils.defaultIfNull(event.getEventGenerator(), DEFAULT_EVENT_GENERATOR_NAME))
    			.origin(event.getOrigin())
    			.type(event.getType())
    			.eventDate(Instant.ofEpochMilli(event.getTimestamp()).atZone(ZoneId.systemDefault()).toLocalDateTime())
    			.build();
    	
    	
    }
    
}
