/**
 * 
 */
package jtimesched;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import de.dominik_geyer.jtimesched.project.Project;
import de.dominik_geyer.jtimesched.project.ProjectException;
import de.dominik_geyer.jtimesched.project.ProjectSerializer;

@DisplayName("ProjectSerializer class")
class ProjectSerializerTest {

	ProjectSerializer serializer;
	
	@BeforeEach
	public void setUp() {
		serializer = new ProjectSerializer("serializer_test_file.xml");
	}


	@Test
	@DisplayName("Write and read xml with a standard project")
	void testWriteReadXmlStandardProject() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		List<Project> oldList = new ArrayList<>();
		List<Project> newList;

		Project project = new Project();
		oldList.add(project);
		
		serializer.writeXml(oldList);
		newList = serializer.readXml();
		
		assertEquals(oldList, newList);
	}
	
	@Test
	@DisplayName("Write and read xml with a checked project")
	void testWriteReadXmlCheckedProject() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		List<Project> oldList = new ArrayList<>();
		List<Project> newList;
		
		Project project = new Project();
		project.setChecked(true);
		oldList.add(project);
		
		serializer.writeXml(oldList);
		newList = serializer.readXml();
		
		assertEquals(oldList, newList);
	}
	
	@Test
	@DisplayName("Write and read xml with a colored project")
	void testWriteReadXmlColoredProject() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException {
		List<Project> oldList = new ArrayList<>();
		List<Project> newList;
		
		Project project = new Project();
		project.setColor(new Color(255, 255, 255));
		oldList.add(project);
		
		serializer.writeXml(oldList);
		newList = serializer.readXml();
		
		assertEquals(oldList, newList);
	}
	
	@Test
	@DisplayName("Write and read xml with a null titled project")
	void testWriteReadXmlNullTitledProject() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException, ProjectException {
		List<Project> oldList = new ArrayList<>();
		List<Project> newList;
		
		Project project = new Project(null);
		oldList.add(project);
		
		serializer.writeXml(oldList);
		newList = serializer.readXml();
		
		assertEquals(oldList, newList);
	}
	
	@Test
	@DisplayName("Write and read xml with a project containing notes")
	void testWriteReadXmlNotedProject() throws TransformerConfigurationException, SAXException, IOException, ParserConfigurationException, ProjectException {
		List<Project> oldList = new ArrayList<>();
		List<Project> newList;
		
		Project project = new Project();
		project.setNotes("project notes");
		
		oldList.add(project);
		
		serializer.writeXml(oldList);
		newList = serializer.readXml();
		
		assertEquals(oldList, newList);
	}
	
}
