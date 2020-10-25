package trenddit.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import trenddit.logic.RuntimeOperations;

import java.io.IOException;

@Component
public class UpdateDBCron {

    private static final Logger log = LoggerFactory.getLogger(UpdateDBCron.class);

    private final RuntimeOperations runtimeOperations;

    public UpdateDBCron(RuntimeOperations runtimeOperations) {
        this.runtimeOperations = runtimeOperations;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateDB() throws InterruptedException, IOException {
        log.info("Update db started...");
        int exitCode = runtimeOperations.execCommand("python3 scripts/src/update_data.py");
        if (exitCode == 0) log.info("Update ended successfully");
        else log.error("Update failed");
    }

    @Scheduled(cron = "0 5 0/1 * * *")
    public void updatePosts() throws IOException, InterruptedException {
        log.info("Update subreddit posts started...");
        int exitCode = runtimeOperations.execCommand("python3 scripts/src/update_posts.py");
        if (exitCode == 0) log.info("Update ended successfully");
        else log.error("Update failed");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateComments() throws IOException, InterruptedException {
        log.info("Update subreddit comments started...");
        int exitCode = runtimeOperations.execCommand("python3 scripts/src/update_comments.py");
        if (exitCode == 0) log.info("Update ended successfully");
        else log.error("Update failed");
    }
}
