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

import org.napalmvin.neuro_log_vui.data.PatientRepository;
import org.napalmvin.neuro_log_vui.entities.Doctor;
import org.napalmvin.neuro_log_vui.entities.Patient;
import org.napalmvin.neuro_log_vui.entities.enums.GenderEnum;
import org.napalmvin.neuro_log_vui.entities.enums.RaceEnum;
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
    public CommandLineRunner loadData(DoctorRepository drRepo,
                                      PatientRepository patRepo) {
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

                createDoctors(imgs);
                createPatients(imgs);

            }
            
            private void createDoctors(List<String> imgs) {
                Doctor dr1 = new Doctor();
                dr1.setFirstName("Jack");
                dr1.setMiddleName("Atilla");
                dr1.setLastName("Bauer");
                dr1.setBirthDate((new GregorianCalendar(1987, 12, 1)).getTime());
                dr1.setGender(GenderEnum.MALE);
                dr1.setRace(RaceEnum.CAUCASIAN);
                dr1.setPhotoName(imgs.get(0));
                dr1.setQualification("Good docctor,MD.");

                Doctor dr2 = new Doctor();
                dr2.setFirstName("Sue");
                dr2.setMiddleName("Mery");
                dr2.setLastName("Yew");
                dr2.setBirthDate((new GregorianCalendar(1975, 12, 1)).getTime());
                dr2.setGender(GenderEnum.FEMALE);
                dr2.setRace(RaceEnum.MONGOLOID);
                dr2.setPhotoName(imgs.get(1));
                dr2.setQualification("Good docctor,MD.");

                drRepo.save(dr1);
                drRepo.saveAndFlush(dr2);

                // fetch all customers
                log.info("Doctors found with findAll():");
                log.info("-------------------------------");
                for (Doctor person : drRepo.findAll()) {
                    log.info(person.toString());
                }
                log.info("");

                // fetch an individual customer by ID
                Doctor person = drRepo.findAll().get(0);
                log.info("drRepo.findAll().get(0):");
                log.info("--------------------------------");
                log.info(person.toString());
                log.info("");

                // fetch customers by last name
                log.info("Doctor found with findByLastNameStartsWithIgnoreCase('Yew'):");
                log.info("--------------------------------------------");
                for (Doctor bauer : drRepo
                        .findByLastNameStartsWithIgnoreCase("Yew")) {
                    log.info(bauer.toString());
                }
                log.info("");
            }

            private void createPatients(List<String> imgs) {
                Patient prsn1 = new Patient();
                prsn1.setFirstName("Галина");
                prsn1.setMiddleName("Василівна");
                prsn1.setLastName("Омелянко");
                prsn1.setBirthDate((new GregorianCalendar(1926, 12, 1)).getTime());
                prsn1.setGender(GenderEnum.FEMALE);
                prsn1.setRace(RaceEnum.CAUCASIAN);
                prsn1.setPhotoName(imgs.get(0));

                Patient prs2 = new Patient();
                prs2.setFirstName("Микола");
                prs2.setMiddleName("Опанасович");
                prs2.setLastName("Гриценко");
                prs2.setBirthDate((new GregorianCalendar(1949, 12, 1)).getTime());
                prs2.setGender(GenderEnum.MALE);
                prs2.setRace(RaceEnum.CAUCASIAN);
                prs2.setPhotoName(imgs.get(1));

                patRepo.save(prsn1);
                patRepo.saveAndFlush(prs2);

                // fetch all customers
                log.info("Doctors found with findAll():");
                log.info("-------------------------------");
                for (Doctor person : drRepo.findAll()) {
                    log.info(person.toString());
                }
                log.info("");

                // fetch an individual customer by ID
                Doctor person = drRepo.findAll().get(0);
                log.info("Doctor found with findOne(1L):");
                log.info("--------------------------------");
                log.info(person.toString());
                log.info("");

                // fetch customers by last name
                log.info("Doctor found with findByLastNameStartsWithIgnoreCase('Yew'):");
                log.info("--------------------------------------------");
                for (Doctor bauer : drRepo
                        .findByLastNameStartsWithIgnoreCase("Yew")) {
                    log.info(bauer.toString());
                }
                log.info("");
            }

        };
    }
}
