package net.intelie.challenges.controller;

import static net.intelie.challenges.domain.infrastructure.utils.EventUtils.createEvent;
import static net.intelie.challenges.domain.infrastructure.utils.EventUtils.getListFromIterator;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.intelie.challenges.domain.infrastructure.utils.CustomStopWatch;
import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventDTO;
import net.intelie.challenges.domain.model.Payload;
import net.intelie.challenges.domain.model.Result;
import net.intelie.challenges.domain.service.impl.EventStoreService;

@Slf4j
@RestController
@RequestMapping("event-store")
@RequiredArgsConstructor
public class EventStoreController {
	
	private final EventStoreService service;	
	
	@PostMapping
	public ResponseEntity<Result> insertEvent(@RequestBody @Valid Payload payload){
      	Event event = createEvent(payload);
      	
		service.insert(event);
		
		return ResponseEntity.ok(Result.builder()
				.type(event.getType())
				.createTime(event.getTimestamp())
				.build());
		
	}
	
	@DeleteMapping
	public ResponseEntity<String> removeAllEventsByType(@RequestParam String eventType){
		@Cleanup("stop")
        StopWatch stopWatch = new CustomStopWatch();
		
		service.removeAll(eventType);	
		
		log.info("All events with type {} were successful removed. Duration {} ms",eventType,stopWatch.getTotalTimeMillis());
		
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body("All events from type were removed");
		
	}
	
	@GetMapping
	public ResponseEntity<List<EventDTO>> findAllByTypeAndRangeTime(
			@RequestParam String eventType,
			@RequestParam long startTime,
			@RequestParam long endTime) throws Exception{
		
		@Cleanup("stop")
        StopWatch stopWatch = new CustomStopWatch();
		
		List<EventDTO> result = getListFromIterator(service.query(eventType, startTime, endTime));		
		log.info("Events recovered successfully.{} Duration: {} ms",result,stopWatch.getTotalTimeMillis());

		return ResponseEntity.status(HttpStatus.OK).body(result);
		
	}

}
