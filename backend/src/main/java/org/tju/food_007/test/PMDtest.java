package org.tju.food_007.test;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;

public class PMDtest {
    public static void main(String[] args) {
        PMDConfiguration configuration = new PMDConfiguration();
        configuration.setInputPaths("D:/Project/2024-Spring-SoftwareTest/software_test_007_food/backend/src/main/java/org/tju/food_007");
        configuration.setRuleSets("rulesets/java/unusedcode.xml");
        configuration.setReportFormat("xml");
        configuration.setReportFile("D:/Project/2024-Spring-SoftwareTest/software_test_007_food/backend/src/main/java/org/tju/food_007/pmd-report.xml");

        PMD.doPMD(configuration);
    }
}
