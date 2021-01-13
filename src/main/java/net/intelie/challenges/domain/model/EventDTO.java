package net.intelie.challenges.domain.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

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
	
    private LocalDateTime eventDate;
    
    public static EventDTO toEventDTO(Event event) {
    	
    	return EventDTO.builder()
    			.origin(event.getOrigin())
    			.type(event.getType())
    			.eventDate(Instant.ofEpochMilli(event.getTimestamp()).atZone(ZoneId.systemDefault()).toLocalDateTime())
    			.build();
    	
    	
    }
    
}
