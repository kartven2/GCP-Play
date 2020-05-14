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

import com.alpha.university.model.Program;
import com.alpha.university.repository.ProgramRepository;

@Service
public class ProgramService {

	@Autowired
	private ProgramRepository programRepository;

	public List<Program> findAll() {
		final var iterable = programRepository.findAll();
		final var programs = new ArrayList<Program>();
		iterable.forEach(s -> programs.add(s));
		return programs;
	}

	public Long count() {
		return programRepository.count();
	}

	private Optional<Program> find(final Long id) {
		if (null == id) {
			throw new IllegalArgumentException("ID has not been specified");
		}
		return programRepository.findById(id);
	}

	public Program findById(final Long id) {
		return find(id).get();
	}

	public Program findByIdOrNull(final Long id) {
		return find(id).orElse(null);
	}

	public void save(final Program program) {
		programRepository.save(program);
	}

	public void deleteById(final Long id) {
		programRepository.deleteById(id);
	}

	public List<Program> findByName(final String name) {
		return programRepository.findByNameIgnoreCaseOrderByNameAsc(name);
	}

	public void saveAll(Iterable<Program> pgms) {
		programRepository.saveAll(pgms);
	}
}
