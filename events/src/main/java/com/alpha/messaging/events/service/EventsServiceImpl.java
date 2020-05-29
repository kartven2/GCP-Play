/*-
Copyright [2020] Karthik.V

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.alpha.messaging.events.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.ProjectTopicName;
import com.google.pubsub.v1.PubsubMessage;

import com.alpha.messaging.events.models.Events;
import com.alpha.messaging.events.models.Message;
import com.alpha.messaging.events.models.Payload;
import com.alpha.messaging.events.repository.EventsRepository;

@Service
public class EventsServiceImpl implements EventsService {

	private static final Logger LOG = LoggerFactory.getLogger(EventsServiceImpl.class);

	@Autowired
	private EventsRepository eventsRepository;

	@Value("${pubsub.projectid}")
	private String projectId;

	@Value("${pubsub.topic}")
	private String topic;

	@Value("${pubsub.token}")
	private String token;

	private Publisher publisher;

	private void validateEvent(String name, String details) {
		if (null == name || name.isBlank()) {
			throw new IllegalArgumentException("Event name has not been specified");
		}
		if (null == details || details.isBlank()) {
			throw new IllegalArgumentException("Event details has not been specified");
		}
	}

	@PostConstruct
	public void init() throws IOException {
		LOG.info("Initializing publisher projectId: {}, topicId {}", projectId, topic);
		publisher = Publisher.newBuilder(ProjectTopicName.of(projectId, topic)).build();
		LOG.info("Completed initializing publisher projectId: {}, topicId {}", projectId, topic);
	}

	@PreDestroy
	public void tearDown() {
		if (null != publisher) {
			publisher.shutdown();
			try {
				publisher.awaitTermination(1, TimeUnit.MINUTES);
			} catch (InterruptedException e) {

			} finally {
				publisher = null;
			}
		}
	}

	@Override
	public Events generateEvent(Events event) {
		return eventsRepository.create(event);
	}

	@Override
	public List<Events> findAllEvents() {
		return eventsRepository.findAllEvents();
	}

	@Override
	public List<Events> findEventsWithLimit(int limit) {
		if (limit <= 0) {
			throw new IllegalArgumentException("Invalid limit has been provided");
		}
		return eventsRepository.findEventsWithLimit(limit);
	}

	@Override
	public Events closeEvent(String id) {
		if (null == id || id.isBlank()) {
			throw new IllegalArgumentException("Event ID has not been specified");
		}
		return eventsRepository.closeEvent(id);
	}

	@Override
	public Events buildEvent(String name, String details, String description) {
		validateEvent(name, details);
		UUID uuid = UUID.randomUUID();
		return new Events(uuid.toString(), name, description, details);
	}

	@Override
	public String serializeEvent(Events event) {
		if (null == event)
			return "";
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(event);
		} catch (Exception e) {
			throw new IllegalStateException(String.format("Unable to serialize event object: %s. Reason: %s",
					event.toString(), e.getMessage()));
		}
	}

	@Override
	public Events deserializeEvent(String jsonEvent) {
		if (null == jsonEvent || jsonEvent.isBlank())
			return null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			LOG.info("Received payload: {}", jsonEvent);
			LOG.info("Starting deserialization...");
			Payload payload = mapper.readValue(jsonEvent, Payload.class);
			LOG.info("Completed deserialization...");
			Message message = payload.getMessage();
			LOG.info("Extracted message: {}", message.getData());
			Events event = mapper.readValue(new String(Base64.getDecoder().decode(message.getData())), Events.class);
			return event;
		} catch (Exception e) {
			throw new IllegalStateException(
					String.format("Unable to de-serialize string: %s. Reason: %s", jsonEvent, e.getMessage()));
		}
	}

	@Override
	public void validateSubscriberRequestToken(String reqToken) {
		LOG.info("Received token: {}", reqToken);
		if (null == reqToken || reqToken.isBlank()) {
			throw new IllegalArgumentException("Token has not been specified");
		}
		if (!reqToken.equals(token)) {
			throw new IllegalArgumentException("Token is invalid");
		}
	}

	@Override
	public void publishEvent(Events event) {
		final String payload = serializeEvent(event);
		LOG.info("Publishing payload: {}", payload);
		PubsubMessage pubsubMessage = PubsubMessage.newBuilder().setData(ByteString.copyFromUtf8(payload)).build();
		publisher.publish(pubsubMessage);
	}

}
