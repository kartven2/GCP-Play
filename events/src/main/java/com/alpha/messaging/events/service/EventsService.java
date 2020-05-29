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

import java.util.List;

import com.alpha.messaging.events.models.Events;

public interface EventsService {

	Events generateEvent(Events event);

	Events closeEvent(String id);

	List<Events> findAllEvents();

	List<Events> findEventsWithLimit(int limit);

	Events buildEvent(String name, String details, String description);

	void publishEvent(Events event);

	String serializeEvent(Events event);

	Events deserializeEvent(String jsonEvent);

	void validateSubscriberRequestToken(String reqToken);
}
