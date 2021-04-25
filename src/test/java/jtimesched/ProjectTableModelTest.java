package jtimesched;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.dominik_geyer.jtimesched.JTimeSchedApp;
import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;

@DisplayName("Tests for ProjectTableModel class")
class ProjectTableModelTest {

	private ProjectTableModel table;

	@BeforeEach
	public void setUp() throws Exception {
		table = new ProjectTableModel(new ArrayList<Project>());
		JTimeSchedApp.LOGGER = Logger.getLogger("JTimeSched");
		JTimeSchedApp.LOGGER.setLevel(Level.ALL);
	}

	@Nested
	@DisplayName("Tests for getColumnName method")
	class TestGetColumnName {

		// Tests for Boundary Analysis

		@Test
		@DisplayName("getColumnName with column index -1")
		public void testColumnFirstOffPoint() {
			assertEquals("", table.getColumnName(-1), "If column cannot be found, should return an empty string");
		}

		@Test
		@DisplayName("getColumnName with the first existent column index")
		public void testColumnFirstOnPoint() {
			assertNotNull(table.getColumnName(0), "Get of an existent column should return a String");
		}

		@Test
		@DisplayName("getColumnName with column index 8")
		public void testColumnSecondOffPoint() {
			assertEquals("", table.getColumnName(8), "If column cannot be found, should return an empty string");
		}

		@Test
		@DisplayName("getColumnName with the last existent column index")
		public void testColumnSecondOnPoint() {
			assertNotNull(table.getColumnName(7), "Get of an existent column should return a String");
		}

		// Tests for Category-Partitioning

		@Test
		@DisplayName("getColumnName with negative column index")
		public void testColumnNegative() {
			assertEquals("", table.getColumnName(-1), "If column cannot be found, should return an empty string");
		}

		@Test
		@DisplayName("getColumnName with an existent column index")
		public void testColumnNotNull() {
			assertNotNull(table.getColumnName(ProjectTableModel.COLUMN_TITLE),
					"Get of an existent column should return a String");
		}

		@Test
		@DisplayName("getColumnName with an inexistent positive column index")
		public void testColumnInexistent() {
			assertEquals("", table.getColumnName(table.getColumnCount() + 100),
					"If column cannot be found, should return an empty string");
		}
		
	}

	@Nested
	@DisplayName("Tests for getProjectAt method")
	class TestGetProjectAt {
		
		// Tests for Category-Partitioning

		@Test
		@DisplayName("getProjectAt with negative row index")
		public void testGetProjectAtNegativeLine() {
			assertNull(table.getProjectAt(-1), "Obtaining an inexistent project should return null");
		}

		@Test
		@DisplayName("getProjectAt with existent project")
		public void testGetProject() {
			Project expected = new Project("title");
			table.addProject(expected);
			Project actual = table.getProjectAt(0);

			assertEquals(expected, actual, "Project obtained should be the same");
		}

		@Test
		@DisplayName("getProjectAt with inexisting project")
		public void testGetProjectAtInexistingLine() {
			assertNull(table.getProjectAt(100), "Obtaining an inexistent project should return null");
		}
		
	}
	
	@Nested
	@DisplayName("Tests for getValueAt method")
	class TestGetValueAt {

		// Tests for Category-Partitioning
		
		@Test
		@DisplayName("getValueAt with a negative row index")
		public void testGetValueAtWithNegativeLine() {
			assertNull(table.getValueAt(-1, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should return null");
		}

		@Test
		@DisplayName("getValueAt with valid input (row and column)")
		public void testGetValueAtWithExistingProject() {
			Project project = new Project("title");
			String expected = project.getTitle();
			table.addProject(project);

			assertEquals(expected, table.getValueAt(0, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("getValueAt with row index inexisting project")
		public void testGetValueAtWithInexistingProject() {
			assertNull(table.getValueAt(0, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should return null");
		}

		@Test
		@DisplayName("getValueAt with negative column index")
		public void testGetValueAtWithNegativeColumn() {
			table.addProject(new Project("title"));

			assertNull(table.getValueAt(0, -1),
					"Obtaining an inexistent column should return null");
		}

		@Test
		@DisplayName("getValueAt with an invalid column index")
		public void testGetValueAtWithNonExistentColumn() {
			table.addProject(new Project("title"));

			assertNull(table.getValueAt(0, table.getColumnCount() + 100),
					"If column cannot be found, should should return null");
		}
		
		// Branch Coverage tests

		@Test
		@DisplayName("Check column with unchecked project")
		public void testGetValueAtColumnCheckWithUncheckedProject() {
			Project project = new Project();
			project.setChecked(false);
			
			table.addProject(project);
			
			assertEquals(false, table.getValueAt(0, ProjectTableModel.COLUMN_CHECK));
		}
		
		@Test
		@DisplayName("Check column with checked project")
		public void testGetValueAtColumnCheckWithCheckedProject() {
			Project project = new Project();
			project.setChecked(true);
			
			table.addProject(project);
			
			assertEquals(true, table.getValueAt(0, ProjectTableModel.COLUMN_CHECK));
		}
		
		@Test
		@DisplayName("Color column")
		public void testGetValueAtColumnColor() {
			Project project = new Project();
			
			Color pColor = new Color(255, 255, 255);	// White
			project.setColor(pColor);
			
			table.addProject(project);

			assertEquals(pColor, table.getValueAt(0, ProjectTableModel.COLUMN_COLOR));
		}
		
		@Test
		@DisplayName("Created column")
		public void testGetValueAtColumnDateOfCreation() {
			Project project = new Project();
			Date creationDate = new Date();
			project.setTimeCreated(creationDate);
			
			table.addProject(project);

			assertEquals(creationDate, table.getValueAt(0, ProjectTableModel.COLUMN_CREATED));
		}
		
		@Test
		@DisplayName("Time Overall column")
		public void testGetValueAtColumnOverallTime() {
			Project project = new Project();
			int seconds = 86400;	// One full day
			project.setSecondsOverall(seconds);
			
			table.addProject(project);

			assertEquals(seconds, table.getValueAt(0, ProjectTableModel.COLUMN_TIMEOVERALL));
		}
		
		@Test
		@DisplayName("Time Today column")
		public void testGetValueAtColumnTimeToday() {
			Project project = new Project();
			int seconds = 60;
			project.setSecondsToday(seconds);
			
			table.addProject(project);

			assertEquals(seconds, table.getValueAt(0, ProjectTableModel.COLUMN_TIMETODAY));
		}
		
		@Test
		@DisplayName("Delete Action column")
		public void testGetValueAtColumnDelete() throws ProjectException {
			Project project = new Project();
			project.start();
			
			table.addProject(project);

			assertEquals(true, table.getValueAt(0, ProjectTableModel.COLUMN_ACTION_DELETE));
		}
		
		@Test
		@DisplayName("Start/Pause Action column")
		public void testGetValueAtColumnStartPause() throws ProjectException {
			Project project = new Project();
//			project.pause();
			table.addProject(project);

			assertEquals(false, table.getValueAt(0, ProjectTableModel.COLUMN_ACTION_STARTPAUSE));
		}
	}

	@Nested
	@DisplayName("Tests for isCellEditable method")
	class TestIsCellEditable {

		// Tests for Boundary Analysis

		@Test
		@DisplayName("isCellEditable with a first off-point row index")
		public void testIsCellEditableWithFirstRowOffPoint() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(-1, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with first on-point row index (row and column)")
		public void testIsCellEditableWithFirstRowOnPoint() {
			table.addProject(new Project("title"));

			assertTrue(table.isCellEditable(0, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with second off-point row index")
		public void testIsCellEditableWithSecondRowOffPoint() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(1, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with first off-point column index")
		public void testIsCellEditableFirstOffPointColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, -1));
		}

		@Test
		@DisplayName("isCellEditable with first on-point column index")
		public void testIsCellEditableFirstOnPointColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, 0));
		}

		@Test
		@DisplayName("isCellEditable with second on-point column index")
		public void testIsCellEditableSecondOnPointColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, 7));
		}

		@Test
		@DisplayName("isCellEditable with second off-point column index")
		public void testIsCellEditableSecondOffPointColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, 8));
		}

		// Tests for Category-Partitioning

		@Test
		@DisplayName("isCellEditable with a negative row index")
		public void testIsCellEditableWithNegativeLine() {
			assertFalse(table.isCellEditable(-1, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with valid input (row and column)")
		public void testIsCellEditableWithExistingProject() {
			table.addProject(new Project("title"));

			assertTrue(table.isCellEditable(0, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with row index of inexisting project")
		public void testIsCellEditableWithInexistingProject() {
			assertFalse(table.isCellEditable(0, ProjectTableModel.COLUMN_TITLE));
		}

		@Test
		@DisplayName("isCellEditable with negative column index")
		public void testIsCellEditableNegativeColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, -1));
		}

		@Test
		@DisplayName("isCellEditable with an invalid column index")
		public void testIsCellEditableWithNonExistentColumn() {
			table.addProject(new Project("title"));

			assertFalse(table.isCellEditable(0, table.getColumnCount() + 100));
		}
		
		// Branch Coverage tests
		
		@Test
		@DisplayName("Time Overall with project running")
		public void testIsCellEditableOverallTimeWithProjectRunning() throws ProjectException {
			Project project = new Project();
			project.start();
			table.addProject(project);

			assertFalse(table.isCellEditable(0, ProjectTableModel.COLUMN_TIMEOVERALL));
		}
		
		@Test
		@DisplayName("Time Today with project paused")
		public void testIsCellEditableTimeTodayWithProjectPaused() throws ProjectException {
			Project project = new Project();
//			project.pause();
			table.addProject(project);

			assertTrue(table.isCellEditable(0, ProjectTableModel.COLUMN_TIMETODAY));
		}
	}
	
	@Nested
	@DisplayName("Tests for getColumnClass")
	class TestGetColumnClass {
		
		@Test
		@DisplayName("Column Color's class")
		public void testGetColumnClass() {
			assertEquals(Color.class, table.getColumnClass(ProjectTableModel.COLUMN_COLOR));
		}
		
		@Test
		@DisplayName("Column Created's class")
		public void testGetColumnClassDateOfCreation() {
			assertEquals(Date.class, table.getColumnClass(ProjectTableModel.COLUMN_CREATED));
		}
		
		@Test
		@DisplayName("Column Overall Time's class")
		public void testGetColumnClassOverallTime() {
			assertEquals(Integer.class, table.getColumnClass(ProjectTableModel.COLUMN_TIMEOVERALL));
		}
		
		@Test
		@DisplayName("Column Time Today's class")
		public void testGetColumnClassTimeToday() {
			assertEquals(Integer.class, table.getColumnClass(ProjectTableModel.COLUMN_TIMEOVERALL));
		}
		
		@Test
		@DisplayName("Column Check's class")
		public void testGetColumnClassCheck() {
			assertEquals(Boolean.class, table.getColumnClass(ProjectTableModel.COLUMN_CHECK));
		}
		
		@Test
		@DisplayName("Unkwown column's class")
		public void testGetColumnClassUnkwown() {
			assertEquals(String.class, table.getColumnClass(table.getColumnCount() + 1));
		}
	}
	
	@Nested
	@DisplayName("Tests for setValueAt")
	class TestSetValueAt {
		
		@Test
		@DisplayName("Setting a project as checked")
		public void testSetValueAtChecked() {
			Project project = new Project();
			project.setChecked(false);
			
			table.addProject(project);
			table.setValueAt(true, 0, ProjectTableModel.COLUMN_CHECK);
			
			assertTrue(project.isChecked());
		}
		
		@Test
		@DisplayName("Setting a project as unchecked")
		public void testSetValueAtUnchecked() {
			Project project = new Project();
			project.setChecked(true);
			
			table.addProject(project);
			table.setValueAt(false, 0, ProjectTableModel.COLUMN_CHECK);
			
			assertFalse(project.isChecked());
		}
		
		@Test
		@DisplayName("Setting a project title")
		public void testSetValueAtTitle() {
			Project project = new Project("old title");
			String newTitle = "new title";
			
			table.addProject(project);
			table.setValueAt(newTitle, 0, ProjectTableModel.COLUMN_TITLE);
			
			assertEquals(newTitle, table.getValueAt(0, ProjectTableModel.COLUMN_TITLE));
		}
		
		@Test
		@DisplayName("Setting a project color")
		public void testSetValueAtColor() {
			Color oldColor = new Color(0, 255, 0);	// Green
			Color newColor = new Color(255, 0, 0);	// Red
			
			Project project = new Project();
			project.setColor(oldColor);
			
			table.addProject(project);
			table.setValueAt(newColor, 0, ProjectTableModel.COLUMN_COLOR);
			
			assertEquals(newColor, table.getValueAt(0, ProjectTableModel.COLUMN_COLOR));
		}
		
		@Test
		@DisplayName("Setting a project time of creation")
		public void testSetValueAtTimeOfCreation() {
			Date oldDate = new Date(0);
			Date newDate = new Date(60000);	// One month difference
			
			Project project = new Project();
			project.setTimeCreated(oldDate);
			
			table.addProject(project);
			table.setValueAt(newDate, 0, ProjectTableModel.COLUMN_CREATED);
			
			assertEquals(newDate, table.getValueAt(0, ProjectTableModel.COLUMN_CREATED));
		}
		
		@Test
		@DisplayName("Setting a project overall time")
		public void testSetValueAtOverallTime() {
			int oldSeconds = 0;
			int newSeconds = 60;	// One minute difference
			
			Project project = new Project();
			project.setSecondsOverall(oldSeconds);
			
			table.addProject(project);
			table.setValueAt(newSeconds, 0, ProjectTableModel.COLUMN_TIMEOVERALL);
			
			assertEquals(newSeconds, table.getValueAt(0, ProjectTableModel.COLUMN_TIMEOVERALL));
		}
		
		@Test
		@DisplayName("Setting a project time today")
		public void testSetValueAtTimeToday() {
			int oldSeconds = 0;
			int newSeconds = 60;	// One minute difference
			
			Project project = new Project();
			project.setSecondsToday(oldSeconds);
			
			table.addProject(project);
			table.setValueAt(newSeconds, 0, ProjectTableModel.COLUMN_TIMETODAY);
			
			assertEquals(newSeconds, table.getValueAt(0, ProjectTableModel.COLUMN_TIMETODAY));
		}
		
		@Test
		@DisplayName("Setting an unexisting project property")
		public void testSetValueAtUnexistingProperty() {
			Project project = new Project();
			int unexistingColumn = table.getColumnCount() + 1;
			
			table.addProject(project);
			table.setValueAt(null, 0, unexistingColumn);
			
			assertNull(table.getValueAt(0, unexistingColumn));
		}
	}
	
	@Nested
	@DisplayName("Tests for removeProject")
	class TestRemoveProject {
		
		@Test
		@DisplayName("Removing existing project")
		public void testRemoveProject() {
			table.addProject(new Project());
			table.removeProject(0);
			
			assertEquals(0, table.getRowCount(), "No projects should remain after removal");
		}
	}
}
