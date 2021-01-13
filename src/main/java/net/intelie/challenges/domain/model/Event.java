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
	
	private String id;
	
	private EventType type;

	private OriginType origin;
	
	@Builder.Default
	private String eventGenerator = "Very-Crazy-Service";
	
    private long timestamp;
    
}
