package net.intelie.challenges.domain.model;

import java.io.Serializable;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is just an event stub, feel free to expand it if needed.
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("Event")
public class Event implements Serializable {

	private static final long serialVersionUID = -7718031121649348203L;
	
	private String id; // a key that ensure the unicity of events in database
	
	private EventType type; //here i created some domain of event types based on daily events of services in general

	private OriginType origin; //here i created some domain of origin types based on daily generical services
	
	private String eventGenerator; // identify some random event generator.
	
    private long timestamp; // mark a time of event registry
    
}
