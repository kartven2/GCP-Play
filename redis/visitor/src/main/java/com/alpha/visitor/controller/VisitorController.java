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

package com.alpha.visitor.controller;

import java.net.SocketException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alpha.visitor.listener.JedisPoolFactory;
import com.alpha.visitor.model.Visitor;
import com.alpha.visitor.service.VisitorService;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("/")
public class VisitorController {

	@Autowired
	private VisitorService visitorService;

	@Autowired
	private JedisPoolFactory jedisPoolFactory;

	private void updateStaticInfo(Model model) {
		model.addAttribute("year", LocalDate.now().getYear());
	}

	private void loadVisitorInfo(Model model, Visitor visitor) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Visitor Details");
		model.addAttribute("content", "info");
		model.addAttribute("scriptOnLoad", "");
		model.addAttribute("vid", visitor.getId());
		model.addAttribute("vname", visitor.getName());
		model.addAttribute("vcount", Long.toString(visitor.getCounter()));
	}

	private void loadError(Model model, Exception ex) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Error");
		model.addAttribute("content", "genericerr");
		model.addAttribute("scriptOnLoad", "");
		model.addAttribute("errmsg", ex.getMessage());
	}

	@GetMapping("/")
	public String health(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Site Health Check");
		model.addAttribute("content", "health");
		model.addAttribute("scriptOnLoad", "showClock()");
		return "main";
	}

	@GetMapping("/visitor")
	public String home(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Visitor Home");
		model.addAttribute("content", "home");
		model.addAttribute("scriptOnLoad", "");
		return "main";
	}

	@GetMapping("/visitor/{id}")
	public String visit(@PathVariable(value = "id") String id, Model model) {
		Visitor visitor = null;
		try {
			visitor = visitorService.visit(id);
		} catch (final Exception ex) {
			loadError(model, ex);
			return "main";
		}
		loadVisitorInfo(model, visitor);
		return "main";
	}

	@PostMapping(path = "/register", consumes = "application/json", produces = "application/json")
	public String register(@RequestBody String name, Model model) {
		Visitor visitor = null;
		try {
			visitor = visitorService.save(name);
		} catch (final Exception ex) {
			loadError(model, ex);
			return "main";
		}
		loadVisitorInfo(model, visitor);
		return "main";
	}

	@GetMapping("/register")
	public String register(Model model) {
		updateStaticInfo(model);
		model.addAttribute("ttl", "Visitor Registration");
		model.addAttribute("content", "register");
		model.addAttribute("scriptOnLoad", "");
		return "main";
	}

	@GetMapping("/tconnect")
	public String tconnect(Model model) {
		try {
			JedisPool jedisPool = jedisPoolFactory.getJedisPool();

			if (jedisPool == null) {
				throw new SocketException("Error connecting to Jedis pool");
			}
			Long visits;

			try (Jedis jedis = jedisPool.getResource()) {
				visits = jedis.incr("visits");
			}
			updateStaticInfo(model);
			model.addAttribute("ttl", "Visitor Test Connect");
			model.addAttribute("content", "tconnect");
			model.addAttribute("scriptOnLoad", "");
			model.addAttribute("result", "Global Visit Count : " + Long.toString(visits));
			return "main";
		} catch (Exception e) {
			loadError(model, e);
			return "main";
		}

	}
}
