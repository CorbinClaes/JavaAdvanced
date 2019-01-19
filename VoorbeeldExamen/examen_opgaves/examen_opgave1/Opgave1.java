package examen_opgave1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Opgave1 {
    public static void main(String[] args) {
        CustomerRepository customerRepository = new CustomerRepository();
        System.out.println("*** Klanten uit Louisville:");
        // TODO: 1
        System.out.println(customerRepository.findAll()
                .stream()
                .filter(customer -> customer.getCity().equalsIgnoreCase("Louisville"))
                .count()
        );

        System.out.println("*** Jarige klanten: ");
        // TODO: 2
        LocalDate now = LocalDate.now();
        customerRepository.findAll()
                .stream()
                .filter(customer ->
                        customer.getDateOfBirth().getMonthValue() == now.getMonthValue() &&
                                customer.getDateOfBirth().getDayOfMonth() == now.getDayOfMonth())
                .forEach(System.out::println);

        System.out.println("*** 10 jongste klanten:");
        // TODO: 3
        customerRepository.findAll()
                .stream()
                .sorted((customer1, customer2) -> customer1.getDateOfBirth().compareTo(customer2.getDateOfBirth()))
                .limit(10)
                .forEach(System.out::println);

        ActivityProcessor activityFileProcessor = new ActivityProcessor(customerRepository);
        List<Activity> allActivities = new ArrayList<>();
        // TODO: 4
        try {
            Path resourcesPath = Paths.get(".\\VoorbeeldExamen\\examen_resources\\opgave1\\");
            Path errorFilePath = Paths.get(".\\VoorbeeldExamen\\examen_resources\\opgave1\\log\\errors.log");
            if (!Files.exists(resourcesPath)) {
                Files.createDirectories(resourcesPath);
                Files.createFile(resourcesPath);
            }
            if (!Files.exists(errorFilePath)) {
                Files.createDirectories(errorFilePath);
                Files.createFile(errorFilePath);
            }
            //Door de directory wandelen en elk bestandje checken (directories zijn ook bestanden dus die wegfilteren)
            Files.walk(resourcesPath)
                    .parallel()
                    .filter(p -> !p.toFile().isDirectory())
                    .forEach(file -> new ActivityProcessor(customerRepository).processActivities(resourcesPath, errorFilePath));
        } catch (IOException ioe) {
            System.out.println("An error occurred during TODO 4.");
            ioe.printStackTrace();
        }

        System.out.println("*** Top 10 klanten");
        // TODO: 5

        System.out.println("** Alle activiteiten meest actieve klant (gesorteerd op datum):");
        // TODO: 6

    }
}
