package org.napalmvin.neuro_log_vui;

import org.napalmvin.neuro_log_vui.data.DoctorRepository;
import java.io.IOException;
import java.nio.file.DirectoryIteratorException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import static org.napalmvin.neuro_log_vui.Constants.Type.IMAGE;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@ComponentScan(basePackages = {"org.napalmvin.neuro_log_vui", "org.napalmvin.neuro_log_vui.ui.doctor"})
@PropertySource("classpath:application.properties")

public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean(name = "msg")
    public ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("Messages", new Locale("uk", "UA"));
    }

    @Bean
    @Autowired
    public CommandLineRunner loadData(DoctorRepository repository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                Path scanImgDir = Paths.get(IMAGE.getFullFolder());
                List<String> imgs = new ArrayList<>();
                try (DirectoryStream<Path> stream = Files.newDirectoryStream(scanImgDir, "*.{jpg}")) {
                    for (Path entry : stream) {
                        imgs.add(entry.getFileName().toString());
                    }
                } catch (IOException | DirectoryIteratorException x) {
                    log.error("Whoops", x);
                }

                // save a couple of customers
                Doctor dr1 = new Doctor();
                dr1.setFirstName("Jack");
                dr1.setMiddleName("Atilla");
                dr1.setLastName("Bauer");
                dr1.setBirthDate((new GregorianCalendar(1987, 12, 1)).getTime());
                dr1.setGender(GenderEnum.MALE);
                dr1.setRace(RaceEnum.Caucasian);
                dr1.setPhotoName(imgs.get(0));
                dr1.setQualification("Good docctor,MD.");
               
                Doctor dr2 = new Doctor();
                dr2.setFirstName("Sue");
                dr2.setMiddleName("Mery");
                dr2.setLastName("Yew");
                dr2.setBirthDate((new GregorianCalendar(1975, 12, 1)).getTime());
                dr2.setGender(GenderEnum.FEMALE);
                dr2.setRace(RaceEnum.Mongoloid);
                dr2.setPhotoName(imgs.get(1));
                dr2.setQualification("Good docctor,MD.");
                
                repository.save(dr1);
                repository.saveAndFlush(dr2);
                
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
            }
        };
    }
}
