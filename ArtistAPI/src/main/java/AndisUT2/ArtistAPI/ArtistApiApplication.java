package AndisUT2.ArtistAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArtistApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArtistApiApplication.class, args);
	}

}
