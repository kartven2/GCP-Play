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

package com.alpha.messaging.events.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.alpha.messaging.events.models.Events;
import com.alpha.messaging.events.service.EventsService;

@Controller
public class EventsController {

	@Autowired
	private EventsService eventsService;

	private void updateStaticInfo(Model model) {
		model.addAttribute("year", LocalDate.now().getYear());
	}

	private void loadError(Model model, Exception ex) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Error");
		model.addAttribute("content", "genericerr");
		model.addAttribute("scriptOnLoad", "");
		model.addAttribute("errmsg", ex.getMessage());
	}

	private void loadEventsInfo(Model model, List<Events> events) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Events Details");
		model.addAttribute("content", "info");
		model.addAttribute("scriptOnLoad", "");
		model.addAttribute("events", events);
	}

	private void loadResult(Model model, Events event) {
		String result = String.format("Event message sent successfully for %s, with details: %s and description: %s",
				event.getName(), event.getDetails(), event.getDescription());
		updateStaticInfo(model);
		model.addAttribute("ttl", "Event Sent");
		model.addAttribute("content", "eventsent");
		model.addAttribute("scriptOnLoad", "");
		model.addAttribute("result", result);
	}

	@GetMapping("/events/health")
	public String health(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Site Health Check");
		model.addAttribute("content", "health");
		model.addAttribute("scriptOnLoad", "showClock()");
		return "main";
	}

	@GetMapping(value = { "/events/", "/events/home" })
	public String home(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Events Home");
		model.addAttribute("content", "home");
		model.addAttribute("scriptOnLoad", "");
		return "main";
	}

	@GetMapping(value = "/events/close")
	public String closeEventMenu(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Events Close");
		model.addAttribute("content", "closeEvent");
		model.addAttribute("scriptOnLoad", "");
		return "main";
	}

	@GetMapping("/events/list")
	public String list(Model model) {
		try {
			loadEventsInfo(model, eventsService.findAllEvents());
		} catch (Exception ex) {
			loadError(model, ex);
		}
		return "main";
	}

	@PostMapping(path = "/events/publish", consumes = "application/json", produces = "application/json")
	public String publishEvent(@RequestBody Map<String, String> map, Model model) {
		try {
			Events event = eventsService.buildEvent(map.get("name"), map.get("details"), map.get("description"));
			eventsService.publishEvent(event);
			loadResult(model, event);
		} catch (final Exception ex) {
			loadError(model, ex);
		}
		return "main";
	}

	@PutMapping(path = "/events/close", consumes = "application/json", produces = "application/json")
	public String closeEvent(@RequestBody Map<String, String> map, Model model) {
		try {
			eventsService.closeEvent(map.get("eid"));
			loadEventsInfo(model, eventsService.findAllEvents());
		} catch (Exception ex) {
			loadError(model, ex);
		}
		return "main";
	}

	@PostMapping(path = "/events/subscriber", consumes = "application/json", produces = "application/json")
	public void subscriber(@RequestParam(required = true) String reqToken, @RequestBody String payload,
			HttpServletResponse response) {
		try {
			eventsService.validateSubscriberRequestToken(reqToken);
			Events event = eventsService.deserializeEvent(payload);
			eventsService.generateEvent(event);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (IllegalArgumentException ex) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} catch (Exception ex) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
