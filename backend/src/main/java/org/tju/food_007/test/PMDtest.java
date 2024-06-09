package org.tju.food_007.test;
import net.sourceforge.pmd.PMD;
import net.sourceforge.pmd.PMDConfiguration;

public class PMDtest {
    public static void main(String[] args) {
        PMDConfiguration configuration = new PMDConfiguration();
        configuration.setInputPaths("D:/Project/2024-Spring-SoftwareTest/software_test_007_food/backend/src/main/java/org/tju/food_007");
        configuration.setRuleSets(
                "category/java/bestpractices.xml/UnusedFormalParameter," +
                        "category/java/bestpractices.xml/UnusedLocalVariable," +
                        "category/java/bestpractices.xml/UnusedPrivateField," +
                        "category/java/bestpractices.xml/UnusedPrivateMethod"
        );
        configuration.setReportFormat("xml");
        configuration.setReportFile("D:/Project/2024-Spring-SoftwareTest/software_test_007_food/backend/src/main/java/org/tju/food_007/pmd-report.xml");

        PMD.doPMD(configuration);
    }
}
