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

package com.alpha.university.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Course {

	@Id
	private Long id;

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	@Column(name = "year_commenced")
	private Integer yearCommenced;

	@NotNull
	@Column(name = "credit_points")
	private Integer creditPoints;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Program program;

	@OneToMany(mappedBy = "studentCourseKey.course", cascade = { CascadeType.ALL })
	private Set<Attempts> attempts = new HashSet<>();

	public Course() {
	}

	public Course(Long id, String name, Integer yearCommenced, Integer creditPoints) {
		super();
		this.id = id;
		this.name = name;
		this.yearCommenced = yearCommenced;
		this.creditPoints = creditPoints;
	}

	public Program getProgram() {
		return program;
	}

	public void setProgram(Program program) {
		this.program = program;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYearCommenced() {
		return yearCommenced;
	}

	public void setYearCommenced(Integer yearCommenced) {
		this.yearCommenced = yearCommenced;
	}

	public Integer getCreditPoints() {
		return creditPoints;
	}

	public void setCreditPoints(Integer creditPoints) {
		this.creditPoints = creditPoints;
	}

	public Set<Attempts> getAttempts() {
		return attempts;
	}

	public void setAttempts(Set<Attempts> attempts) {
		this.attempts = attempts;
	}

}
