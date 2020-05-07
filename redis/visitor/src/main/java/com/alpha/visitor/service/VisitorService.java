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

package com.alpha.visitor.service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.visitor.model.Visitor;

@Service
public class VisitorService {

	private static final int ID_LEN = 5;

	@Autowired
	private VisitorRepository visitorRepository;

	private Visitor findById(final String id) {
		if (null == id) {
			throw new IllegalArgumentException("No visitor ID specified");
		}
		Optional<Visitor> visitorOptional = visitorRepository.findById(id);
		Visitor visitor = null;
		try {
			visitor = visitorOptional.get();
		} catch (NoSuchElementException ex) {
			throw new NoSuchElementException(String.format("No visitor found with ID: %s", id));
		}
		return visitor;
	}

	private Visitor findByIdOrNull(final String id) {
		if (null == id)
			return null;
		Optional<Visitor> visitorOptional = visitorRepository.findById(id);
		try {
			return visitorOptional.get();
		} catch (NoSuchElementException ex) {
			return null;
		}
	}

	private String getRandomString() {
		int[] asciiCharLimit = { 97, 122 };
		StringBuilder sb = new StringBuilder(ID_LEN);
		for (int i = 0; i < ID_LEN; i++) {
			int nextRandomChar = asciiCharLimit[0]
					+ (int) (ThreadLocalRandom.current().nextFloat() * (asciiCharLimit[1] - asciiCharLimit[0] + 1));
			sb.append((char) nextRandomChar);
		}
		final String timeNow = Long.toString(System.currentTimeMillis());
		sb.append(timeNow.substring(timeNow.length() - 5));
		return sb.toString();
	}

	public Visitor save(final String name) {
		if (null == name) {
			throw new IllegalArgumentException("No visitor name specified");
		}

		String vid = null;
		do {
			vid = getRandomString();
		} while (findByIdOrNull(vid) != null);

		synchronized (vid) {
			if (findByIdOrNull(vid) != null) {
				return save(name);
			}
			return visitorRepository.save(new Visitor(vid, name, 1));
		}
	}

	public Visitor visit(final String id) {
		Visitor visitor = findById(id);
		String vid = visitor.getId();
		synchronized (vid) {
			visitor.setCounter(visitor.getCounter() + 1);
			return visitorRepository.save(visitor);
		}
	}
}
