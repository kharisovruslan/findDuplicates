package my.findDuplicates;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FindDuplicatesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FindDuplicatesApplication.class, args);
        openHomePage("http://localhost:8080");
    }

    private static void openHomePage(String url) {
        if (Desktop.isDesktopSupported()) {
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(new URI(url));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
