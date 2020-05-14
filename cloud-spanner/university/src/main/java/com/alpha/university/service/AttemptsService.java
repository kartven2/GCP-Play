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

package com.alpha.university.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alpha.university.model.Attempts;
import com.alpha.university.repository.AttemptsRepository;

@Service
public class AttemptsService {

	@Autowired
	private AttemptsRepository attemptsRepository;

	public List<Attempts> findAll() {
		final var iterable = attemptsRepository.findAll();
		final var attemptss = new ArrayList<Attempts>();
		iterable.forEach(s -> attemptss.add(s));
		return attemptss;
	}

	public Long count() {
		return attemptsRepository.count();
	}

	private Optional<Attempts> find(final Long id) {
		if (null == id) {
			throw new IllegalArgumentException("ID has not been specified");
		}
		return attemptsRepository.findById(id);
	}

	public Attempts findById(final Long id) {
		return find(id).get();
	}

	public Attempts findByIdOrNull(final Long id) {
		return find(id).orElse(null);
	}

	public void save(final Attempts attempts) {
		attemptsRepository.save(attempts);
	}

	public void deleteById(final Long id) {
		attemptsRepository.deleteById(id);
	}

	public void saveAll(Iterable<Attempts> pgms) {
		attemptsRepository.saveAll(pgms);
	}
}
