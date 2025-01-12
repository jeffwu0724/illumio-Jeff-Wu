import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FlowLogProcessror {
    // use _portProtocolTags to keep track of
    // dstport,   protocol,   tag
    // 25,        tcp,        sv_P1
    private Map<PortProtocolPair, String> _portProtocolTags = new HashMap<>();
    private Map<String, Integer> _tagOfPortProtocolCount = new HashMap<>();
    private  Map<PortProtocolPair, Integer> _portProtocolTagsCount = new HashMap<>();

    public void initialPortProtocolTagsMap(String lookupTableFile) throws IOException{
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(lookupTableFile))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lookupTableLine = line.trim().split(",");
                // if the lookupTableFile format is wrong, we will skip
                if (lookupTableLine.length < 3) continue;

                String dstPort = lookupTableLine[0].trim();
                String protocol = lookupTableLine[1].toLowerCase().trim();
                String tag = lookupTableLine[2].toLowerCase().trim();

                //we will base on the lookpu table, and create a map with PortProtocolPair + tag
                PortProtocolPair curPortProtocolPair = new PortProtocolPair(dstPort, protocol);
                _portProtocolTags.put(curPortProtocolPair, tag);
            }
        }
    }

    public void processFlowLogFile(String flowLogFile) throws IOException {
        // use BufferReader to read in the sample_flow_log.csv, and then we process each line of it
        // in the given example,
        // 2 123456789012 eni-0a1b2c3d 10.0.1.201 198.51.100.2 443 49153 6 25 20000 1620140761 1620140821 ACCEPT OK
        // we will only focus on protocl, which is 6, and the destination port which is 49153, so we will try to transform this into a string array.
        // and get [6] for port, [7] for protocol

        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(flowLogFile))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] sampleFlowLineArray = line.trim().split("\\s+");
                // if the flow log format is wrong, we will skip
                if (sampleFlowLineArray.length < 14) continue;

                String dstPort = sampleFlowLineArray[6];
                String protocol = sampleFlowLineArray[7];
                String tag = null;

                PortProtocolPair curPortProtocolPair = new PortProtocolPair(dstPort, protocol);
                //take care of "Count of matches for each tag, sample o/p"
                if(_portProtocolTags.containsKey(curPortProtocolPair)){
                    //get the tag based on the desPort and protocol
                    tag = _portProtocolTags.get(curPortProtocolPair);
                    //update the _tagOfPortProtocolCount, if we already have a record, then we use it + 1, else, we initial with 1
                    _tagOfPortProtocolCount.put(tag, _tagOfPortProtocolCount.getOrDefault(tag, 0) + 1);
                }else{
                    _tagOfPortProtocolCount.put("Untagged", _tagOfPortProtocolCount.getOrDefault("Untagged", 0) + 1);
                }

                //take care of "Count of matches for each port/protocol combination"
                _portProtocolTagsCount.put(curPortProtocolPair, _portProtocolTagsCount.getOrDefault(curPortProtocolPair, 0) + 1);
            }
        }
    }

    public void generateTagCountOutputReport() throws IOException {
        String outputFile = "output/tag_count_report.csv";
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(outputFile))) {
            printWriter.println("Tag Counts:");
            printWriter.println("Tag,Count");

            for (Map.Entry<String, Integer> entry : _tagOfPortProtocolCount.entrySet()) {
                printWriter.println(entry.getKey() + "," + entry.getValue());
            }
        }
    }

    public void generatePortProtocolCombinationCountOutputReport() throws IOException {
        String outputFile = "output/port_protocol_com_count_report.csv";
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(outputFile))) {
            printWriter.println("Port/Protocol Combination Counts: ");
            printWriter.println("Port,Protocol,Count");

            for (Map.Entry<PortProtocolPair, Integer> entry : _portProtocolTagsCount.entrySet()) {
                printWriter.println(entry.getKey() + "," + entry.getValue());
            }
        }
      
    }

}
