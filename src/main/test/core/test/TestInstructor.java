package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestInstructor {
	private IAdmin admin;
	private IInstructor instructor;

	@Before
	public void setup() {
		this.admin = new Admin();
		this.admin.createClass("Test", 2017, "Instructor", 15);
	    this.instructor = new Instructor();
	}
	
	//Test instructor adding homework
	@Test
	public void testAddHomework1() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		assertTrue(instructor.homeworkExists("Test", 2017, "Homework"));
	}
	
	//Test instructor needs to be teaching class to assign homework
	@Test
	public void testAddHomework2() {
		instructor.addHomework("WrongName", "Test", 2017, "Homework");
		assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	}
	
	//Test adding homework for non-existent class does not create class
	@Test
	public void testAddHomework3() {
		instructor.addHomework("Instructor", "nonexist", 2017, "Homework");
		assertFalse(admin.classExists("nonexist", 2017));
	}
	
	//Test adding homework does not affect class with same name in different year
	@Test
	public void testAddHomework4() {
		admin.createClass("Test", 2018, "Instructor", 15);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		assertFalse(instructor.homeworkExists("Test", 2018, "Homework"));
	}
	
	//Test adding homework does not affect class in same year with different name
	@Test
	public void testAddHomework5() {
		admin.createClass("Test2", 2017, "Instructor", 15);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		assertFalse(instructor.homeworkExists("Test2", 2017, "Homework"));
	}
	
	//Test null instructor name
	@Test
	public void testAddHomework6() {
		try {
			instructor.addHomework(null, "Test", 2017, "Homework");
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	}
	
	//Test null class name
	@Test
	public void testAddHomework7() {
		try {
			instructor.addHomework("Instructor", null, 2017, "Homework");
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.homeworkExists(null, 2017, "Homework"));
	}
	
	//Test null homework name
	@Test
	public void testAddHomework8() {
		try {
			instructor.addHomework("Instructor", "Test", 2017, null);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.homeworkExists("Test", 2017, null));
	}
	
	//Test assigning grade
	@Test
	public void testAssignGrade1() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		assertTrue(instructor.getGrade("Test", 2017, "Homework", "Student") == 100);
	}
	
	//Test instructor not assigned to this class
	@Test
	public void testAssignGrade2() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor2", "Test", 2017, "Homework", "Student", 100);
		assertFalse(instructor.getGrade("Test", 2017, "Homework", "Student") == 100);
	}
	
	//Test homework was not assigned
	@Test
	public void testAssignGrade3() {
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		assertFalse(instructor.getGrade("Test", 2017, "Homework", "Student") == 100);
	}
	
	//Test student did not submit
	@Test
	public void testAssignGrade4() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		assertFalse(instructor.getGrade("Test", 2017, "Homework", "Student") == 100);
	}
	
	//Test given grade equal to 0
	@Test
	public void testAssignGrade5() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 0);
		assertTrue(instructor.getGrade("Test", 2017, "Homework", "Student") == 0);
	}
	
	//Test given grade less than 0
	@Test
	public void testAssignGrade6() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", -1);
		assertFalse(instructor.getGrade("Test", 2017, "Homework", "Student") == -1);
	}
	
	//Test null instructor
	@Test
	public void testAssignGrade7() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		try {
			instructor.assignGrade(null, "Test", 2017, "Homework", "Student", 100);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.getGrade("Test", 2017, "Homework", "Student") == 100);
	}
	
	//Test null class name
	@Test
	public void testAssignGrade8() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		try {
			instructor.assignGrade("Instructor", null, 2017, "Homework", "Student", 100);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.getGrade(null, 2017, "Homework", "Student") == 100);
	}
	
	//Test null homework name
	@Test
	public void testAssignGrade9() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		try {
			instructor.assignGrade("Instructor", "Test", 2017, null, "Student", 100);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(instructor.getGrade("Test", 2017, null, "Student") == 100);
	}
	
	//Test null student name
	@Test
	public void testAssignGrade10() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		try {
			instructor.assignGrade("Instructor", "Test", 2017, "Homework", null, 100);
		} catch (NullPointerException e) {
			fail();
		}
		assertTrue(instructor.getGrade("Test", 2017, "Homework", null) == 100);
	}
	
	//Test assigning grade does not create homework if it did not exist
	@Test
	public void testAssignGrade11() {
		Student student = new Student();
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		instructor.assignGrade("Instructor", "Test", 2017, "Homework", "Student", 100);
		assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	}
}
