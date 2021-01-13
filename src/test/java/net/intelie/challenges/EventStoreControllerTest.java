package net.intelie.challenges;

import static net.intelie.challenges.TestUtils.createInvalidPayload;
import static net.intelie.challenges.TestUtils.createPayload;
import static net.intelie.challenges.TestUtils.createSetOfEvents;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.SneakyThrows;
import net.intelie.challenges.controller.EventStoreController;
import net.intelie.challenges.domain.model.Event;
import net.intelie.challenges.domain.model.EventDTO;
import net.intelie.challenges.domain.model.EventType;
import net.intelie.challenges.domain.model.Payload;
import net.intelie.challenges.domain.model.Result;
import net.intelie.challenges.domain.service.impl.EventStoreService;
import net.intelie.challenges.domain.service.impl.RedisService;

@RunWith(MockitoJUnitRunner.class)
public class EventStoreControllerTest {

	@Mock
	private RedisTemplate<String, Event> redisTemplate;

	@Mock
	private RedisService redisService;
	
	@Spy
	@InjectMocks
	private EventStoreService service;	
	
	private EventStoreController controller;
	
	
	@Before
	public void init() {
		controller = new EventStoreController(service);
        setField(redisService, "redisTemplate", redisTemplate);
       
	}
		
	@Test
	@SneakyThrows
	public void testSuccessfulQuery() {
		
		Set<Event> events = createSetOfEvents();
		
		Event event = events.stream().findFirst().get();
		
		long endTime = event.getTimestamp()+100000l; //enlarge the range time for now timestamp issues
		
		doReturn(createSetOfEvents()).when(redisService).query(eq(EventType.BATCH.toString()), anyLong(), eq(endTime));
		
		ResponseEntity<List<EventDTO>> responseList = controller.findAllByTypeAndRangeTime(EventType.BATCH.toString(), 0l, endTime);
		
		assertTrue(responseList.getStatusCode() == HttpStatus.OK);
		assertTrue(responseList.getBody().size() ==1);
		assertTrue(responseList.getBody().get(0).getEventGenerator().equals(event.getEventGenerator()));
		assertTrue(responseList.getBody().get(0).getOrigin().equals(event.getOrigin()));
		assertTrue(responseList.getBody().get(0).getType().equals(event.getType()));
		assertTrue(responseList.getBody().get(0).getEventDate().isBefore(Instant.ofEpochMilli(endTime).atZone(ZoneId.systemDefault()).toLocalDateTime()));

	    	
	}
	
	@Test
	@SneakyThrows
	public void testEmptyResultQuery() {
		
		doReturn(null).when(redisService).query(eq(EventType.BATCH.toString()), anyLong(), anyLong());
		
		ResponseEntity<List<EventDTO>> responseList = controller.findAllByTypeAndRangeTime(EventType.BATCH.toString(), 0l, 23434123412341l);
		
		
		assertTrue(responseList.getStatusCode() == HttpStatus.OK);
		assertTrue(responseList.getBody().size() == 0);


	    	
	}
		
	@Test
	public void testSuccessfulInsert() {
		
		Payload payload = createPayload();
	
		ResponseEntity<Result> response = controller.insertEvent(payload);
		
		assertTrue(response.getStatusCode() == HttpStatus.OK);
		assertTrue(response.getBody().getType().toString().equals(payload.getType()));
		assertTrue(response.getBody().getCreateTime() <= Instant.now().toEpochMilli());

	    	
	}
	
	
	
	@Test
	public void testFailInsert() {			
		
        assertThrows(IllegalArgumentException.class, ()->controller.insertEvent(createInvalidPayload()));
        verify(service, never()).insert(any(Event.class));
	    	
	}



}