package com.jn.backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class BackendApplication {

    private static final Logger logger = Logger.getLogger(BackendApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (args.length != 2) {
                logger.log(Level.WARNING, "Please provide output and input JSON filepath.");
                return;
            }

            String inputFilePath = args[0];
            String outputFilePath = args[1];

            try {
                runSimulation(inputFilePath, outputFilePath);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error occurred: " + e.getMessage());
            }
        };
    }

    private void runSimulation(String inputFilePath, String outputFilePath) throws Exception {
        String inputJson = new String(Files.readAllBytes(Paths.get(inputFilePath)));
        RestTemplate restTemplate = new RestTemplate();

        sendPostRequest(outputFilePath, inputJson, restTemplate);
    }

    static void sendPostRequest(String outputFilePath, String inputJson, RestTemplate restTemplate) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(inputJson, headers);

        String url = "http://localhost:8080/simulation";
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
        Files.write(Paths.get(outputFilePath), response.getBody().getBytes());
    }

}
