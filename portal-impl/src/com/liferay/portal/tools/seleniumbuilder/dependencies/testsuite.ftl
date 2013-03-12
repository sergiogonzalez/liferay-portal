package ${seleniumBuilderContext.getTestSuitePackageName(testSuiteName)};

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.StopSeleniumTest;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import junit.framework.TestSuite;

public class ${seleniumBuilderContext.getTestSuiteSimpleClassName(testSuiteName)} extends BaseTestSuite {

	public static TestSuite testSuite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(StopSeleniumTest.class);

		return testSuite;
	}

}