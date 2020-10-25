package trenddit.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class RuntimeOperations {

    private static final Logger log = LoggerFactory.getLogger(RuntimeOperations.class);

    public int execCommand(String command) throws IOException, InterruptedException  {
        Process p = Runtime.getRuntime().exec(createCommand(command));
        logCommandOut(p);
        return p.waitFor();
    }

    private String[] createCommand(String command) {
        String[] toReturn = new String[3];

        if (System.getProperty("os.name").startsWith("Windows")) {
            toReturn[0] = "cmd.exe";
            toReturn[1] = "/c";
        } else {
            toReturn[0] = "/bin/bash";
            toReturn[1] = "-c";
        }
        toReturn[2] = command;
        return toReturn;
    }

    private void logCommandOut(Process p) throws IOException {
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        log.info("Standard output of the command:");
        StringBuilder standardOut = new StringBuilder();
        StringBuilder errorOut = new StringBuilder();

        String outLine;
        while ((outLine = stdInput.readLine()) != null) {
            standardOut.append(outLine);
            standardOut.append('\n');
        }
        if (!standardOut.toString().equals("")) {
            log.info(standardOut.toString());
        }

        while ((outLine = stdError.readLine()) != null) {
            errorOut.append(outLine);
            errorOut.append('\n');
        }
        if (!errorOut.toString().equals("")) {
            log.error("Error while updating db:");
            log.error(errorOut.toString());
        }
    }
}
