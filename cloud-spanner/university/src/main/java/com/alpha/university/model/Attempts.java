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

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Attempts {

	@EmbeddedId
	private StudentCourseKey studentCourseKey;

	private Integer mark;

	private String grade;

	public Attempts() {
	}

	public Attempts(StudentCourseKey studentCourseKey, Integer mark, String grade) {
		super();
		this.studentCourseKey = studentCourseKey;
		this.mark = mark;
		this.grade = grade;
	}

	public StudentCourseKey getStudentCourseKey() {
		return studentCourseKey;
	}

	public void setStudentCourseKey(StudentCourseKey studentCourseKey) {
		this.studentCourseKey = studentCourseKey;
	}

	public Integer getMark() {
		return mark;
	}

	public void setMark(Integer mark) {
		this.mark = mark;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
