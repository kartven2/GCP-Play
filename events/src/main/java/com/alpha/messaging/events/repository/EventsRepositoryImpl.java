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

package com.alpha.messaging.events.repository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.alpha.messaging.events.models.Events;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.EntityQuery.Builder;
import com.google.cloud.datastore.Key;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;

@Component
public class EventsRepositoryImpl implements EventsRepository {

	private static final Logger LOG = LoggerFactory.getLogger(EventsRepositoryImpl.class);
	private static final String EVENT_MESSAGES = "eventMessages";

	@Autowired
	ResourceLoader resourceLoader;

	private KeyFactory keyFactory;
	private Datastore datastore;

	@PostConstruct
	public void init() throws IOException {
		LOG.info("Initializing DataStore.... {}", EVENT_MESSAGES);
		datastore = DatastoreOptions.getDefaultInstance().getService();
		keyFactory = datastore.newKeyFactory().setKind(EVENT_MESSAGES);
		LOG.info("Initializing DataStore Completed.... {}", EVENT_MESSAGES);
	}

	@PreDestroy
	public void destroy() {
		keyFactory.reset();
		keyFactory = null;
		datastore = null;
	}

	@Override
	public Events create(Events events) {
		Key key = keyFactory.newKey(events.getId());
		Entity.Builder eventEntityBuilder = Entity.newBuilder(key).set("id", events.getId())
				.set("name", events.getName()).set("details", events.getDetails()).set("open", events.isOpen())
				.set("startTime", new Timestamp(System.currentTimeMillis()).toString());
		if (null != events.getDescription()) {
			eventEntityBuilder.set("description", events.getDescription());
		}
		if (null != events.getEndTime()) {
			eventEntityBuilder.set("endTime", events.getEndTime().toString());
		}
		Entity entity = eventEntityBuilder.build();
		LOG.info("About to create entity in datastore {}", entity.toString());
		datastore.put(entity);
		return events;
	}

	private Events getEventFromEntity(Entity entity) {
		Events event = new Events(entity.getString("id"), entity.getString("name"), entity.getString("details"),
				Timestamp.valueOf(entity.getString("startTime")));
		event.setOpen(entity.getBoolean("open"));
		if (entity.contains("description")) {
			event.setDescription(entity.getString("description"));
		}
		if (entity.contains("endTime")) {
			event.setEndTime(Timestamp.valueOf(entity.getString("endTime")));
		}
		return event;
	}

	private List<Events> findEvents(final Integer limit) {
		Builder qb = Query.newEntityQueryBuilder().setKind(EVENT_MESSAGES);
		if (null != limit) {
			qb.setLimit(limit);
		}
		Query<Entity> query = qb.addOrderBy(StructuredQuery.OrderBy.desc("startTime")).build();
		QueryResults<Entity> results = datastore.run(query);
		List<Events> events = new ArrayList<>();
		while (results.hasNext()) {
			Entity entity = results.next();
			events.add(getEventFromEntity(entity));
		}
		return events;
	}

	@Override
	public List<Events> findAllEvents() {
		return findEvents(null);
	}

	@Override
	public List<Events> findEventsWithLimit(int limit) {
		return findEvents(limit);
	}

	@Override
	public Events closeEvent(String id) {
		Key key = keyFactory.newKey(id);
		Entity entity = Entity.newBuilder(datastore.get(key))
				.set("endTime", new Timestamp(System.currentTimeMillis()).toString()).set("open", false).build();
		datastore.update(entity);
		return getEventFromEntity(entity);
	}

}
