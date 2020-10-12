package trenddit.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UpdateDBCron {

    private static final Logger log = LoggerFactory.getLogger(UpdateDBCron.class);

    @Scheduled(cron = "0 0 0 * * *")
    public void updateDB() throws InterruptedException, IOException {
        log.info("Update db started...");

        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String[] command = new String[3];

        if (System.getProperty("os.name").startsWith("Windows")) {
            command[0] = "cmd.exe";
            command[1] = "/c";
        } else {
            command[0] = "/bin/bash";
            command[1] = "-c";
        }
        command[2] = "python3 scripts/src/update_data.py > scripts/" + currentDate + "-out.log";

        Process p = Runtime.getRuntime().exec(command);

        int exitCode = p.waitFor();
        if (exitCode == 0) log.info("Update ended successfully");
        else log.info("Update failed, please check script/" + currentDate + "-out.log for more info");
    }
}
