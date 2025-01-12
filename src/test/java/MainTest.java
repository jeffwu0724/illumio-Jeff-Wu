package test.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;



public class MainTest {

    private void _compareFiles(String actualFilePath, String expectedFilePath) throws IOException {
        List<String> actualLines = Files.readAllLines(Paths.get(actualFilePath));
        List<String> expectedLines = Files.readAllLines(Paths.get(expectedFilePath));

        Assertions.assertEquals(expectedLines, actualLines);
    }

    @Test
    public void testTagCountReportComparison() throws IOException {
        String actualFilePath = "src/output/tag_count_report.csv";
        String expectedFilePath = "src/TagCountExpectedResult.csv";
        _compareFiles(actualFilePath, expectedFilePath);
    }

    @Test
    public void testPortProtocolCombinationCountReportComparison() throws IOException {
        String actualFilePath = "src/output/port_protocol_com_count_report.csv";
        String expectedFilePath = "src/PortProtocolCombinationCountExpectedResult.csv";
        _compareFiles(actualFilePath, expectedFilePath);
    }
}
