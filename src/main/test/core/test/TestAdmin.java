package core.test;

import core.api.IAdmin;
import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {

    private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    }

    //Test class creation
    @Test
    public void testMakeClass1() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    //Test creation of class in the past
    @Test
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    //Test creation of class in the future
    @Test
    public void testMakeClass3() {
    	admin.createClass("Test", 2018, "Instructor", 15);
    	assertTrue(admin.classExists("Test", 2018));
    }
    
    //Test creation with capacity of zero
    @Test
    public void testMakeClass4() {
    	this.admin.createClass("Test", 2017, "Instructor", 0);
    	assertFalse(this.admin.classExists("Test", 2017));
    }
    
    //Test creation with negative capacity
    @Test
    public void testMakeClass5() {
    	this.admin.createClass("Test", 2017, "Instructor", -10);
    	assertFalse(this.admin.classExists("Test", 2017));
    }
    
    //Test className/year pair must be unique
    @Test
    public void testMakeClass6() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.createClass("Test", 2017, "Instructor2", 15);
    	assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
    	assertFalse(this.admin.getClassInstructor("Test", 2017).equals("Instructor2"));
    }
    
    //Test instructor can be assigned up to 2 courses per year
    @Test
    public void testMakeClass7() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.createClass("Test2", 2017, "Instructor", 15);
    	this.admin.createClass("Test", 2018, "Instructor", 15);
    	this.admin.createClass("Test2", 2018, "Instructor", 15);
    	assertTrue(this.admin.classExists("Test", 2017));
    	assertTrue(this.admin.getClassInstructor("Test", 2017).equals("Instructor"));
    	assertTrue(this.admin.classExists("Test2", 2017));
    	assertTrue(this.admin.getClassInstructor("Test2", 2017).equals("Instructor"));
    	assertTrue(this.admin.classExists("Test", 2018));
    	assertTrue(this.admin.getClassInstructor("Test", 2018).equals("Instructor"));
    	assertTrue(this.admin.classExists("Test2", 2018));
    	assertTrue(this.admin.getClassInstructor("Test2", 2018).equals("Instructor"));
    }
    
    //Test instructor can be assigned no more than 2 courses per year
    @Test
    public void testMakeClass8() {
    	this.admin.createClass("Test", 2017, "Instructor", 15);
    	this.admin.createClass("Test2", 2017, "Instructor", 15);
    	this.admin.createClass("Test3", 2017, "Instructor", 15);
    	assertTrue(admin.classExists("Test", 2017));
    	assertTrue(admin.classExists("Test2", 2017));
    	assertFalse(admin.classExists("Test3", 2017));
    }
    
    //Test class creation with null for name
    @Test
    public void testMakeClass9() {
    	try {
    		admin.createClass(null, 2017, "Instructor", 15);
    	} catch (NullPointerException e) {
    		fail();
    	}
    	assertFalse(admin.classExists(null, 2017));
    }
    
    //Test class creation with null for instructor
    @Test
    public void testMakeClass10() {
    	try {
    		admin.createClass("Test", 2017, null, 15);
    	} catch (NullPointerException e) {
    		fail();
    	}
    	assertFalse(admin.classExists("Test", 2017));
    }
    
    //Test changing capacity of a class
    @Test
    public void testChangeCapacity1() {
    	admin.createClass("Test", 2017, "Instructor", 15);
    	admin.changeCapacity("Test", 2017, 20);
    	assertTrue(admin.getClassCapacity("Test", 2017) == 20);
    }
    
    //Test changing capacity to lower capacity
    @Test
    public void testChangeCapacity2() {
    	admin.createClass("Test", 2017, "Instructor", 15);
    	admin.changeCapacity("Test", 2017, 10);
    	assertTrue(admin.getClassCapacity("Test", 2017) == 15);
    }
    
    //Test changing capacity to lower capacity with greater absolute value
    @Test
    public void testChangeCapacity3() {
    	admin.createClass("Test", 2017, "Instructor", 15);
    	admin.changeCapacity("Test", 2017, -20);
    	assertTrue(admin.getClassCapacity("Test", 2017) == 15);
    }
    
    //Test changing capacity of one class does not affect other classes of same name
    @Test
    public void testChangeCapacity4() {
    	admin.createClass("Test", 2017, "Instructor", 15);
    	admin.createClass("Test", 2018, "Instructor", 15);
    	admin.changeCapacity("Test", 2017, 20);
    	assertTrue(admin.getClassCapacity("Test", 2018) == 15);
    }
    
    //Test changing capacity of one class does not affect other classes of same year
    @Test
    public void testChangeCapacity5() {
    	admin.createClass("Test", 2017, "Instructor", 15);
    	admin.createClass("Test2", 2017, "Instructor", 15);
    	admin.changeCapacity("Test", 2017, 20);
    	assertTrue(admin.getClassCapacity("Test2", 2017) == 15);
    }
    
    //Test changing capacity of a non-existent class does not create a class
    @Test
    public void testChangeCapacity6() {
    	admin.changeCapacity("Test", 2017, 15);
    	assertFalse(admin.classExists("Test", 2017));
    }
}
