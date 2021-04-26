package jtimesched;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;


@DisplayName("Tests for Project class")
class ProjectTest {

	Project project;
	
	@BeforeEach
	public void setUp() throws Exception {
		project = new Project("project title");
	}
	
	@Nested
	@DisplayName("Tests for toggle method")
	class TestToggle {
		
		@Test
		@DisplayName("Test toggle with a running project")
		void testToggleWithRunningProject() {
			try {
				project.start();
				project.toggle();
				assertFalse(project.isRunning());
			} catch (ProjectException e) {
				fail(e);
			}
		}
		
		@Test
		@DisplayName("Test toggle with a paused project")
		void testToggleWithPausedProject() {
				project.toggle();
				assertTrue(project.isRunning());
		}
	}
	
	@Nested
	@DisplayName("Tests for adjustSecondsToday")
	class TestAdjustSecondsToday {
		
		@Test
		@DisplayName("getColumnName with an existent column index")
		public void testColumnNotNull() {
			project.adjustSecondsToday(10);
			assertEquals(10, project.getSecondsToday(),
					"Get of an existent column should return a String");
		}		
		
		@Test
		@DisplayName("getColumnName with an existent column index")
		public void testAdjustSecondsTodayMutation() {
			project.adjustSecondsToday(-1);
			assertEquals(0, project.getSecondsToday(),
					"Get of an existent column should return a String");
		}	
		
		@Test
		@DisplayName("getColumnName with an existent column index")
		public void testAdjustSecondsTodayMutation2() {
			project.setSecondsOverall(30);
			project.setSecondsToday(10);
			project.adjustSecondsToday(4);
			assertEquals(24, project.getSecondsOverall(),
					"Get of an existent column should return a String");
		}	
	}
	
	@Nested
	@DisplayName("Tests for adjustSecondsToday")
	class TestResetToday {
		
		@Test
		@DisplayName("getColumnName with an existent column index")
		public void testColumnNotNull() {
			project.resetToday();
			assertEquals(0, project.getSecondsToday()," Get of an existent column should return a String");
			assertEquals(0, project.getQuotaToday(),
					"Get of an existent column should return a String");
		}		
	}
	
	@Nested
	@DisplayName("Tests for exception branches on functions")
	class Testexceptions {
		
		@Test
		@DisplayName("Test start with a running project")
		void teststartWithRunningProject() {
			try {
				project.start();
				assertThrows(ProjectException.class, () -> project.start(),
						"Should throw an exception when trying to start when the program is already started");
			}
			catch (ProjectException e) {
				fail(e);
			}
			
		}
		
		
//		@Test
//		@DisplayName("Test getElapsedSeconds with a running project")
//		void testgetElapsedSeconds() {
//			try {
//				project.getElapsedSeconds();
//				assertThrows(ProjectException.class, () -> project.getElapsedSeconds(),
//						"Should throw an exception when trying to get ElapsedSeconds when the program is running");
//			}
//			catch (ProjectException e) {
//				fail(e);
//			}
//			
//			
//		}
		
		@Test
		@DisplayName("Test pause with a paused project")
		void testpauseWithpausedProject() {
			assertThrows(ProjectException.class, () -> project.pause(),
					"Should throw an exception when trying to pause when the program is already paused");
		}
		
		@Test
		@DisplayName("Test getSecondsToday with a started project")
		void testgetSecondsTodayWithstartedProject() {
			try {
				project.start();
				project.setSecondsToday(5);
				assertEquals(5, project.getSecondsToday(),
						"Should return 5");
			}
			catch (ProjectException e) {
				fail(e);
			}
			
		}
		
		@Test
		@DisplayName("Test getSecondsOverall with a started project")
		void testgetSecondsOverallWithstartedProject() {
			try {
				project.start();
				assertEquals(0, project.getSecondsOverall(),
						"Should return 0 since zero seconds are being counted but it is started");
			}
			catch (ProjectException e) {
				fail(e);
			}
			
		}
	}
	
	
	@Nested
	@DisplayName("Tests for setting seconds functions with negative numbers")
	class Testseconds {
		
		@Test
		@DisplayName("Test setSecondsOverall with a negative number")
		void testsetSecondsOverallWithNegativeNumber() {
			project.setSecondsOverall(-1);
			assertEquals(0, project.getSecondsOverall()," Seconds should be set to 0");
			
		}
		
		
		@Test
		@DisplayName("Test setsetSecondsToday with a negative number")
		void testsetSecondsTodayWithNegativeNumber() {
			project.setSecondsToday(-1);
			assertEquals(0, project.getSecondsToday()," Seconds should be set to 0");
			
		}
		
		@Test
		@DisplayName("Test adjustSecondsToday with a negative number")
		void testadjustSecondsTodayWithNegativeNumber() {
			project.adjustSecondsToday(-1);
			assertEquals(0, project.getSecondsToday()," Seconds should be set to 0");
			
		}
	}

}
