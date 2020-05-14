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

import java.util.Date;
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
public class Student {

	@Id
	private Long id;

	@NotNull
	@Size(max = 100)
	private String firstname;

	@NotNull
	@Size(max = 100)
	private String lastname;

	@NotNull
	private Date dob;

	@NotNull
	@Column(name = "year_enrolled")
	private Integer yearEnrolled;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	private Program program;

	@OneToMany(mappedBy = "studentCourseKey.student", cascade = { CascadeType.ALL })
	private Set<Attempts> attempts = new HashSet<>();

	public Student() {
	}

	public Student(Long id, String firstname, String lastname, Date dob, Integer yearEnrolled) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.dob = dob;
		this.yearEnrolled = yearEnrolled;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getYearEnrolled() {
		return yearEnrolled;
	}

	public void setYearEnrolled(Integer yearEnrolled) {
		this.yearEnrolled = yearEnrolled;
	}

	public Set<Attempts> getAttempts() {
		return attempts;
	}

	public void setAttempts(Set<Attempts> attempts) {
		this.attempts = attempts;
	}

}
