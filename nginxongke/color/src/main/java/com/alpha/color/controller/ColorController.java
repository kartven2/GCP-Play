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
package com.alpha.color.controller;

import java.net.InetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alpha.color.dto.ColorRepository;
import com.alpha.color.model.Color;

@Controller
@RequestMapping("/")
public class ColorController {

	@Autowired
	private ColorRepository colorRepo;

	@GetMapping("/color/{name}")
	public String getColor(@PathVariable(value = "name") String name, Model model) {
		final Color color = colorRepo.getColorByName(name);
		model.addAttribute("name", color.getName());
		StringBuilder sb = new StringBuilder('(');
		for (int rgbCode : color.getRgb()) {
			sb.append(rgbCode);
			sb.append(',');
		}
		sb.replace(sb.length() - 1, sb.length(), ")");
		model.addAttribute("rgb", sb.toString());
		model.addAttribute("hexCode", color.getHexCode());
		addHostInfo(model);
		return "color";
	}

	private void addHostInfo(Model model) {
		InetAddress ip;
		String hostname;
		try {
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			model.addAttribute("ip", ip);
			model.addAttribute("hostname", hostname);
		} catch (Exception e) {
		}
	}
}
