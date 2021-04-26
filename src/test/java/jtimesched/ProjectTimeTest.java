package jtimesched;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.dominik_geyer.jtimesched.project.ProjectException;
import de.dominik_geyer.jtimesched.project.ProjectTime;

@DisplayName("Tests for ProjectTime class")
class ProjectTimeTest {

	@Nested
	@DisplayName("Tests for formatSeconds method")
	class TestFormatSeconds {

		@Test
		@DisplayName("formatSeconds with value -1")
		public void testFormatSecondsFirstOffPoint() {
			assertEquals("", ProjectTime.formatSeconds(-1), 
					"Negative seconds should return an empty string");
		}

		@Test
		@DisplayName("formatSeconds with value 0")
		public void testFormatSecondsFirstOnPoint() {
			assertEquals("00:00:00", ProjectTime.formatSeconds(0),
					"Should return 00:00:00");
		}

		@Test
		@DisplayName("formatSeconds with value 59")
		public void testFormatSecondsSecondOffPoint() {
			assertEquals("00:00:59", ProjectTime.formatSeconds(59),
					"Should return 00:00:59");
		}
		
		@Test
		@DisplayName("formatSeconds with value 60")
		public void testFormatSecondsSecondOnPoint() {
			assertEquals("00:01:00", ProjectTime.formatSeconds(60),
					"Should return 00:01:00");
		}
		@Test
		@DisplayName("formatSeconds with value 3599")
		public void testFormatThirdSecondOffPoint() {
			assertEquals("00:59:59", ProjectTime.formatSeconds(3599),
					"Should return 00:59:59");
		}
		
		@Test
		@DisplayName("formatSeconds with value 60")
		public void testFormatThirdSecondOnPoint() {
			assertEquals("01:00:00", ProjectTime.formatSeconds(3600),
					"Should return 01:00:00");
		}

	}
	
	@Nested
	@DisplayName("Tests for TestparseSeconds method")
	class TestparseSeconds {

		@Test
		@DisplayName("parseSeconds with value 59 seconds")
		public void TestParseSecondsNegative() throws ProjectException { 
			assertEquals(59, ProjectTime.parseSeconds("00:00:59"),
					"Should return 59");
		}
		@Test
		@DisplayName("parseSeconds with value invalid")
		public void TestParseSecondsException() { 
			assertThrows(ProjectException.class, () -> ProjectTime.parseSeconds("99999999"),
					"Should return an exception");
		}
		
		@Test
		@DisplayName("parseSeconds to cover mutation testing")
		public void TestParseSecondsMutation() throws ProjectException { 
			assertEquals(11099, ProjectTime.parseSeconds("03:04:59"),
					"Should return 11099");
		}
	}
	
	@Nested
	@DisplayName("Tests for formatDate method")
	class formatDate {
		
		@Test
		@DisplayName("FormatDate with null value")
		public void TestFormatDate() {
			assertEquals("", ProjectTime.formatDate(null), "Should return an empty string");   
		}
		
		@Test
		@DisplayName("FormatDate to cover mutation")
		public void TestFormatDateMutation() {
			Date d1 = new Date(2323223232L);
			assertEquals("1970-01-27", ProjectTime.formatDate(d1), "Should return 1970-01-27");   
		}
	}
	
	@Nested
	@DisplayName("Tests for TestparseDate method")
	class TestparseDate {

		@Test
		@DisplayName("parsedate with value -1")
		public void TestParseDate() {
			assertThrows(ProjectException.class, () -> ProjectTime.parseDate("99999999"),
					"weird format should return an exception");
		}
		
		@Test
		@DisplayName("parsedate for mutation")
		public void TestParseDateMutation() throws ProjectException {
			Date d1 = new Date(6, 5, 4, 0, 0, 0);
			assertEquals(d1, ProjectTime.parseDate("1906-6-4"), "Should return Thu Nov 11 00:00:00 WET 2021");
		}
	}
}
