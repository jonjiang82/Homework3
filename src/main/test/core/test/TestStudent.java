package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import core.api.IInstructor;
import core.api.impl.Instructor;
import core.api.IStudent;
import core.api.impl.Student;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	private IAdmin admin;
	private IInstructor instructor;
	private IStudent student;

	@Before
	public void setup() {
		this.admin = new Admin();
		this.admin.createClass("Test", 2017, "Instructor", 5);
	    this.instructor = new Instructor();
	    this.student = new Student();
	}
	
	//Test registering for a class
	@Test
	public void testRegister1() {
		student.registerForClass("Student", "Test", 2017);
		assertTrue(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test class name is wrong and wrong class does not exist
	@Test
	public void testRegister2() {
		student.registerForClass("Student", "Test2", 2017);
		assertFalse(student.isRegisteredFor("Student", "Test2", 2017));
	}
	
	//Test class year is wrong and wrong class does not exist
	@Test
	public void testRegister3() {
		student.registerForClass("Student", "Test", 2018);
		assertFalse(student.isRegisteredFor("Student", "Test", 2018));
	}
	
	//Test registering for non-existent class does not create class
	@Test
	public void testRegister4() {
		student.registerForClass("Student", "Test2", 2017);
		assertFalse(admin.classExists("Test2", 2017));
	}
	
	//Test registering for class past enrollment capacity
	@Test
	public void testRegister5() {
		student.registerForClass("Student1", "Test", 2017);
		student.registerForClass("Student2", "Test", 2017);
		student.registerForClass("Student3", "Test", 2017);
		student.registerForClass("Student4", "Test", 2017);
		student.registerForClass("Student5", "Test", 2017);
		
		student.registerForClass("Student6", "Test", 2017);
		assertFalse(student.isRegisteredFor("Student6", "Test", 2017));
	}
	
	//Test student registering multiple times does not take up extra capacity
	@Test
	public void testRegister6() {
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test", 2017);
		
		student.registerForClass("Student2", "Test", 2017);
		assertTrue(student.isRegisteredFor("Student2", "Test", 2017));
	}
	
	//Test null student name
	@Test
	public void testRegister7() {
		try {
			student.registerForClass(null, "Test", 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertTrue(student.isRegisteredFor(null, "Test", 2017));
	}
	
	//Test null class name
	@Test
	public void testRegister8() {
		try {
			student.registerForClass("Student", null, 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertTrue(student.isRegisteredFor("Student", null, 2017));
	}
	
	//Test dropping a class
	@Test
	public void testDrop1() {
		student.registerForClass("Student", "Test", 2017);
		student.dropClass("Student", "Test", 2017);
		assertFalse(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test dropping a class reduces enrollment count
	@Test
	public void testDrop2() {
		student.registerForClass("Student1", "Test", 2017);
		student.registerForClass("Student2", "Test", 2017);
		student.registerForClass("Student3", "Test", 2017);
		student.registerForClass("Student4", "Test", 2017);
		student.registerForClass("Student5", "Test", 2017);
		
		student.dropClass("Student1", "Test", 2017);
		student.registerForClass("Student6", "Test", 2017);
		assertTrue(student.isRegisteredFor("Student6", "Test", 2017));
	}
	
	//Test dropping a class when not enrolled
	@Test
	public void testDrop3() {
		student.dropClass("Student", "Test", 2017);
		assertFalse(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test null student name
	@Test
	public void testDrop4() {
		student.registerForClass("Student", "Test", 2017);
		try {
			student.dropClass(null, "Test", 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertTrue(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test null class name
	@Test
	public void testDrop5() {
		student.registerForClass("Student", "Test", 2017);
		try {
			student.dropClass("Student", null, 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertTrue(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test dropping class doesn't affect other classes
	@Test
	public void testDrop6() {
		admin.createClass("Test2", 2017, "Instructor2", 5);
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test2", 2017);
		student.dropClass("Student", "Test2", 2017);
		assertTrue(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test dropping future class of same name doesn't affect this class
	@Test
	public void testDrop7() {
		admin.createClass("Test", 2018, "Instructor2", 5);
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student", "Test2", 2017);
		student.dropClass("Student", "Test", 2018);
		assertTrue(student.isRegisteredFor("Student", "Test", 2017));
	}
	
	//Test one student dropping a class doesn't affect other students
	@Test
	public void testDrop8() {
		student.registerForClass("Student", "Test", 2017);
		student.registerForClass("Student2", "Test", 2017);
		student.dropClass("Student", "Test", 2017);
		assertTrue(student.isRegisteredFor("Student2", "Test", 2017));
	}
	
	//Test submit homework
	@Test
	public void testSubmitHomework1() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		assertTrue(student.hasSubmitted("Student", "Homework", "Test", 2017));
	}
	
	//Test homework does not exist
	@Test
	public void testSubmitHomework2() {
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		assertFalse(student.hasSubmitted("Student", "Homework", "Test", 2017));
	}
	
	//Test student is not registered
	@Test
	public void testSubmitHomework3() {
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		assertFalse(student.hasSubmitted("Student", "Homework", "Test", 2017));
	}
	
	//Test wrong year
	@Test
	public void testSubmitHomework4() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		student.submitHomework("Student", "Homework", "Answer", "Test", 2016);
		assertFalse(student.hasSubmitted("Student", "Homework", "Test", 2016));
		assertFalse(student.hasSubmitted("Student", "Homework", "Test", 2017));
	}
	
	//Test null student name
	@Test
	public void testSubmitHomework5() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		try {
			student.submitHomework(null, "Homework", "Answer", "Test", 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(student.hasSubmitted(null, "Homework", "Test", 2017));
	}
	
	//Test null homework name
	@Test
	public void testSubmitHomework6() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		try {
			student.submitHomework("Student", null, "Answer", "Test", 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(student.hasSubmitted("Student", null, "Test", 2017));
	}
	
	//Test null answer
	@Test
	public void testSubmitHomework7() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		try {
			student.submitHomework("Student", "Homework", null, "Test", 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(student.hasSubmitted("Student", "Homework", "Test", 2017));
	}
	
	//Test null class name
	@Test
	public void testSubmitHomework8() {
		student.registerForClass("Student", "Test", 2017);
		instructor.addHomework("Instructor", "Test", 2017, "Homework");
		try {
			student.submitHomework("Student", "Homework", "Answer", null, 2017);
		} catch (NullPointerException e) {
			fail();
		}
		assertFalse(student.hasSubmitted("Student", "Homework", null, 2017));
	}
	
	//Test submit homework should not create homework if it didn't exist
	@Test
	public void testSubmitHomework9() {
		student.registerForClass("Student", "Test", 2017);
		student.submitHomework("Student", "Homework", "Answer", "Test", 2017);
		assertFalse(instructor.homeworkExists("Test", 2017, "Homework"));
	}
}
