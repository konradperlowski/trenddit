package trenddit.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class UpdateDBCron {

    private static final Logger log = LoggerFactory.getLogger(UpdateDBCron.class);

    @Scheduled(cron = "0 0 0 * * *")
    public void updateDB() throws InterruptedException, IOException {
        log.info("Update db started...");

        String[] command = new String[3];

        if (System.getProperty("os.name").startsWith("Windows")) {
            command[0] = "cmd.exe";
            command[1] = "/c";
        } else {
            command[0] = "/bin/bash";
            command[1] = "-c";
        }
        command[2] = "python3 scripts/src/update_data.py";

        Process p = Runtime.getRuntime().exec(command);
        logCommandOut(p);

        int exitCode = p.waitFor();
        if (exitCode == 0) log.info("Update ended successfully");
        else log.error("Update failed");
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
            log.info("Output of db update:");
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
