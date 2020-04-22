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
package com.alpha.color.dto;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.alpha.color.model.Color;

@Component
public class ColorRepository {

	private Map<String, Color> colors;

	public Color getColorByName(String name) {
		Color color = colors.get(name);
		if (null == color) {
			throw new IllegalArgumentException("Unknown color request");
		}
		return color;
	}

	@PostConstruct
	public void setup() {
		colors = new HashMap<>();
		colors.put("red", new Color("red", new int[] { 255, 0, 0 }, "#FF0000"));
		colors.put("green", new Color("green", new int[] { 0, 255, 0 }, "#00FF00"));
		colors.put("blue", new Color("blue", new int[] { 0, 0, 255 }, "#0000FF"));
		colors.put("purple", new Color("purple", new int[] { 128, 0, 128 }, "#800080"));
		colors.put("magenta", new Color("magenta", new int[] { 255, 0, 255 }, "#FF00FF"));
		colors.put("yellow", new Color("yellow", new int[] { 255, 255, 51 }, "#FFFF33"));
		colors.put("white", new Color("white", new int[] { 255, 255, 255 }, "#FFFFFF"));
	}
}
