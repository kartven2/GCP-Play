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

import com.alpha.university.model.Course;
import com.alpha.university.repository.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;

	public List<Course> findAll() {
		final var iterable = courseRepository.findAll();
		final var courses = new ArrayList<Course>();
		iterable.forEach(s -> courses.add(s));
		return courses;
	}

	public Long count() {
		return courseRepository.count();
	}

	private Optional<Course> find(final Long id) {
		if (null == id) {
			throw new IllegalArgumentException("ID has not been specified");
		}
		return courseRepository.findById(id);
	}

	public Course findById(final Long id) {
		return find(id).get();
	}

	public Course findByIdOrNull(final Long id) {
		return find(id).orElse(null);
	}

	public void save(final Course course) {
		courseRepository.save(course);
	}

	public void deleteById(final Long id) {
		courseRepository.deleteById(id);
	}

	public List<Course> findByName(final String name) {
		return courseRepository.findByNameIgnoreCaseOrderByNameAsc(name);
	}

	public void saveAll(Iterable<Course> pgms) {
		courseRepository.saveAll(pgms);
	}
}
