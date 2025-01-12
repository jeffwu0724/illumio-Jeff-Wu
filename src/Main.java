import java.io.IOException;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        Logger logger = Logger.getLogger(Main.class.getName());
        FlowLogProcessror flowLogProcessror = new FlowLogProcessror();
        try {
            flowLogProcessror.initialPortProtocolTagsMap(args[0]);
            flowLogProcessror.processFlowLogFile(args[1]);
            flowLogProcessror.generatePortProtocolCombinationCountOutputReport();
            flowLogProcessror.generateTagCountOutputReport();
        } catch (IOException e) {
            logger.severe("Error processing files: " + e.getMessage());
            System.exit(1);
        }
    }
}