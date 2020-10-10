package trenddit.config;

import net.dean.jraw.RedditClient;
import net.dean.jraw.http.NetworkAdapter;
import net.dean.jraw.http.OkHttpNetworkAdapter;
import net.dean.jraw.http.UserAgent;
import net.dean.jraw.oauth.Credentials;
import net.dean.jraw.oauth.OAuthHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class JRAWConfig {

    @Bean
    public RedditClient getReddit() {
        return OAuthHelper.automatic(
                new OkHttpNetworkAdapter(new UserAgent("bot",
                        "trenddit",
                        "v0.1",
                        "Trenddit")),
                Credentials.userless("UiiCHPMPwVJozA",
                        "AblwWj2rKosVoIflCrpgONjfMEQ",
                        new UUID(2, 3)));
    }
}
