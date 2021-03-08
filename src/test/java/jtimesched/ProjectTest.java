package jtimesched;

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

}
