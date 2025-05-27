package sqat.selenium.tests;

import org.junit.platform.suite.api.IncludeClassNamePatterns;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("sqat.selenium.tests") 
@IncludeClassNamePatterns(".*Test") // Only include classes ending with 'Test'
public class AllTestsSuite {
}