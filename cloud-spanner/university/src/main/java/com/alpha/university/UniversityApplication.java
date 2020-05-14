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

package com.alpha.university;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.alpha.university.model.Attempts;
import com.alpha.university.model.Course;
import com.alpha.university.model.Program;
import com.alpha.university.model.Student;
import com.alpha.university.model.StudentCourseKey;
import com.alpha.university.service.AttemptsService;
import com.alpha.university.service.CourseService;
import com.alpha.university.service.ProgramService;
import com.alpha.university.service.StudentService;

@SpringBootApplication
public class UniversityApplication implements CommandLineRunner {

	private static Logger LOG = LoggerFactory.getLogger(UniversityApplication.class);

	@Autowired
	private StudentService studentService;

	@Autowired
	private ProgramService programService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private AttemptsService attemptsService;

	public static void main(String[] args) {
		SpringApplication.run(UniversityApplication.class, args);
	}

	private void saveObjectGraph() {
		final Student stu = new Student(1l, "Karthik", "Venkataraman", java.sql.Date.valueOf(LocalDate.of(2018, 9, 17)),
				2020);
		final Course cou = new Course(1l, "Course 101", 1912, 3);
		final Program pro = new Program(1l, "Doctor of Medicine", 1912, 30);
		final StudentCourseKey stc = new StudentCourseKey(1, 1);
		final Attempts atm = new Attempts(stc, 92, "A+");
		stu.setProgram(pro);
		stu.getAttempts().add(atm);
		stc.setCourse(cou);
		stc.setStudent(stu);
		cou.setProgram(pro);
		cou.getAttempts().add(atm);
		pro.getCourses().add(cou);
		pro.getStudents().add(stu);
		atm.setStudentCourseKey(stc);
		programService.save(pro);
		// studentService.save(stu);
		// courseService.save(cou);
		// attemptsService.save(atm);
	}

	private String getGrade(double marks) {
		if (marks > 95d) {
			return "A+";
		} else if (marks > 85d) {
			return "A";
		} else if (marks > 75d) {
			return "B+";
		} else if (marks > 65d) {
			return "B";
		} else if (marks > 55d) {
			return "C";
		} else {
			return "D";
		}
	}

	private void populateData() {
		final Program[] programs = { new Program(2l, "Computer Engineering", 1930, 30),
				new Program(3l, "Electrical Engineering", 1940, 40),
				new Program(4l, "Mechanical Engineering", 1975, 35) };
		for (Program pg : programs) {

			for (int i = 0; i < 3; i++) {
				final var courseId = (long) (110 + i) + pg.getId() * 1000;
				final var creditPoints = pg.getId() % 2 == 0 ? 2 : 3;
				final var course = new Course(courseId, "Course " + courseId, pg.getYearCommenced(), creditPoints);
				course.setProgram(pg);
				pg.getCourses().add(course);
			}

			for (int i = 1; i <= 2; i++) {
				final var studentId = (long) (200 + i) + pg.getId() * 1000;
				final var student = new Student(studentId, "fname" + studentId, "lname" + studentId,
						java.sql.Date.valueOf(LocalDate.of(1980 + i, Math.max(i % 13, 1), i)), 2004 + i);
				pg.getStudents().add(student);

				for (Course course : pg.getCourses()) {
					final StudentCourseKey stc = new StudentCourseKey(i % 5, i % 7);
					final double marks = Math.random() * 100;
					final Attempts atm = new Attempts(stc, (int) Math.round(marks), getGrade(marks));
					student.getAttempts().add(atm);
					student.setProgram(pg);
					stc.setCourse(course);
					stc.setStudent(student);
					atm.setStudentCourseKey(stc);
					course.getAttempts().add(atm);
				}
			}
			programService.save(pg);
		}
	}

	public void run(String... args) {
		LOG.info("UniversityApplication has started....");
		saveObjectGraph();
		populateData();
		LOG.info("UniversityApplication has completed....");
	}
}
