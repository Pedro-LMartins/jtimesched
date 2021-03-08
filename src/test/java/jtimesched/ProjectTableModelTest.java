package jtimesched;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;
import de.dominik_geyer.jtimesched.project.ProjectTableModel;

@DisplayName("Tests for ProjectTableModel class")
class ProjectTableModelTest {

	private ProjectTableModel table;

	@BeforeEach
	public void setUp() throws Exception {
		table = new ProjectTableModel(new ArrayList<Project>());
	}

	@Nested
	@DisplayName("Tests for getColumnName method")
	class TestGetColumnName {

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

		@Test
		@DisplayName("getProjectAt with negative row index")
		public void testGetProjectAtNegativeLine() {
			assertThrows(ProjectException.class, () -> table.getProjectAt(-1),
					"Obtaining an inexistent project should raise an exception");
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
			assertThrows(ProjectException.class, () -> table.getProjectAt(100),
					"Obtaining an inexistent project should raise an exception");
		}

	}

	@Nested
	@DisplayName("Tests for getValueAt method")
	class TestGetValueAt {

		@Test
		@DisplayName("getValueAt with a negative row index")
		public void testGetValueAtWithNegativeLine() {
			assertThrows(ProjectException.class, () -> table.getValueAt(-1, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should raise an exception");
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
			assertThrows(ProjectException.class, () -> table.getValueAt(0, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should raise an exception");
		}

		@Test
		@DisplayName("getValueAt with negative column index")
		public void testGetValueAtWithNegativeColumn() {
			table.addProject(new Project("title"));

			assertThrows(ProjectException.class, () -> table.getValueAt(0, -1),
					"Obtaining an inexistent column should raise an exception");
		}

		@Test
		@DisplayName("getValueAt with an invalid column index")
		public void testGetValueAtWithNonExistentColumn() {
			table.addProject(new Project("title"));

			assertThrows(ProjectException.class, () -> table.getValueAt(0, table.getColumnCount() + 100),
					"If column cannot be found, should should raise an exception");
		}

	}

	@Nested
	@DisplayName("Tests for isCellEditable method")
	class TestIsCellEditable {

		@Test
		@DisplayName("isCellEditable with a negative row index")
		public void testIsCellEditableWithNegativeLine() {
			assertThrows(ProjectException.class, () -> table.isCellEditable(-1, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should raise an exception");
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
			assertThrows(ProjectException.class, () -> table.getValueAt(0, ProjectTableModel.COLUMN_TITLE),
					"Obtaining an inexistent project should raise an exception");
		}

		@Test
		@DisplayName("isCellEditable with negative column index")
		public void testIsCellEditableNegativeColumn() {
			table.addProject(new Project("title"));

			assertThrows(ProjectException.class, () -> table.isCellEditable(0, -1),
					"Obtaining an inexistent column should raise an exception");
		}

		@Test
		@DisplayName("isCellEditable with an invalid column index")
		public void testIsCellEditableWithNonExistentColumn() {
			table.addProject(new Project("title"));

			assertThrows(ProjectException.class, () -> table.isCellEditable(0, table.getColumnCount() + 100),
					"If column cannot be found, should should raise an exception");
		}
	}
}
