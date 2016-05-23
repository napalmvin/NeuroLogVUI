package org.napalmvin.neuro_log_vui;

import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.napalmvin.neuro_log_vui.data.RaceEnum;
import org.napalmvin.neuro_log_vui.data.GenderEnum;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    @Autowired
    public CommandLineRunner loadData(DoctorRepository repository) {
        return (args) -> {
            Path scanImgDir = Paths.get(ImageUploadeReceiver.DEFAULT_IMG_FOLDER);
            List<String> imgs = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(scanImgDir, "*.{jpg}")) {
                for (Path entry : stream) {
                    imgs.add(entry.getFileName().toString());
                }
            } catch (IOException | DirectoryIteratorException x) {
                log.error("Whoops", x);
            }

            // save a couple of customers
            repository.save(new Doctor("Jack", "Bauer",new Date(1987, 12, 1),
                    GenderEnum.MALE,RaceEnum.Caucasian,  imgs.get(0),"Good docctor,MD." ));
            repository.save(new Doctor("Chloe", "O'Brian",new Date(1977, 12, 1),
                    GenderEnum.FEMALE,RaceEnum.Caucasian, imgs.get(1),"Good docctor,MD." ));
            repository.save(new Doctor("Kim", "Bauer",new Date(1985, 12, 1),
                    GenderEnum.FEMALE,RaceEnum.Caucasian, imgs.get(2),"Good docctor,MD." ));
            repository.save(new Doctor("David", "Palmer",new Date(1966, 12, 1),
                    GenderEnum.MALE,RaceEnum.Caucasian, imgs.get(3),"Good docctor,MD." ));
            repository.save(new Doctor("Michelle", "Dessler",new Date(1968, 12, 1),
                    GenderEnum.FEMALE,RaceEnum.Caucasian,  imgs.get(0),"Good docctor,MD." ));

            // fetch all customers
            log.info("Doctors found with findAll():");
            log.info("-------------------------------");
            for (Doctor customer : repository.findAll()) {
                log.info(customer.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            Doctor customer = repository.findOne(1L);
            log.info("Doctor found with findOne(1L):");
            log.info("--------------------------------");
            log.info(customer.toString());
            log.info("");

            // fetch customers by last name
            log.info("Doctor found with findByLastNameStartsWithIgnoreCase('Bauer'):");
            log.info("--------------------------------------------");
            for (Doctor bauer : repository
                    .findByLastNameStartsWithIgnoreCase("Bauer")) {
                log.info(bauer.toString());
            }
            log.info("");
        };
    }

}
