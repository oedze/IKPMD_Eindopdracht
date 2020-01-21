package ikpmd.ikpmd.testapplication.services;

import java.util.List;

import ikpmd.ikpmd.testapplication.models.Project;
import ikpmd.ikpmd.testapplication.models.Test;
import ikpmd.ikpmd.testapplication.models.TestData;
import ikpmd.ikpmd.testapplication.models.TestResult;

public class RoundService {

    public static Project project;
    public static List<Test> tests;
    public static int currentTestIndex = 0;
    public static List<TestResult> testResults;

    public static Test getCurrentTest() {
        return tests.get(currentTestIndex);
    }



}
