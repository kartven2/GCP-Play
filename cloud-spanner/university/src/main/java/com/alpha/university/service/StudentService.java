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

import com.alpha.university.model.Student;
import com.alpha.university.repository.StudentRepository;

@Service
public class StudentService {

	@Autowired
	private StudentRepository studentRepository;

	public List<Student> findAll() {
		final var iterable = studentRepository.findAll();
		final var students = new ArrayList<Student>();
		iterable.forEach(s -> students.add(s));
		return students;
	}

	public Long count() {
		return studentRepository.count();
	}

	private Optional<Student> find(final Long id) {
		if (null == id) {
			throw new IllegalArgumentException("ID has not been specified");
		}
		return studentRepository.findById(id);
	}

	public Student findById(final Long id) {
		return find(id).get();
	}

	public Student findByIdOrNull(final Long id) {
		return find(id).orElse(null);
	}

	public void save(final Student student) {
		studentRepository.save(student);
	}

	public void deleteById(final Long id) {
		studentRepository.deleteById(id);
	}

	public List<Student> findByName(final String firstname, final String lastname) {
		return studentRepository.findByFirstnameAndLastnameAllIgnoreCaseOrderByFirstnameAsc(firstname, lastname);
	}

	public void saveAll(Iterable<Student> pgms) {
		studentRepository.saveAll(pgms);
	}
}
