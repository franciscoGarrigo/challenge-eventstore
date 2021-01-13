package net.intelie.challenges.domain.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payload {
	
	@NotBlank(message = "Type cannot be blank")
	private String type;
	
	@NotBlank(message = "Origin cannot be blank")
	private String origin;
	
	private String eventGenerator;

	
}