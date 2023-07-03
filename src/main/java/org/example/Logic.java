package org.example;

import model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class Logic {
    private static final String BASE_URL = "http://94.198.50.185:7081/api/users";
    private String sessionId;

    public String getSessionId() {
        if (sessionId == null) {
            fetchSessionId();
        }
        return sessionId;
    }


    private void fetchSessionId() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(BASE_URL, String.class);

        HttpHeaders headers = response.getHeaders();
        List<String> cookies = headers.get(HttpHeaders.SET_COOKIE);
        if (cookies != null) {
            for (String cookie : cookies) {
                if (cookie.startsWith("JSESSIONID=")) {
                    sessionId = cookie.split(";")[0].substring(11);
                    break;
                }
            }
        }
    }

    public void getAllUsers() {
        if (sessionId == null) {
            System.out.println("No sessionId available");
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.GET, requestEntity, String.class);
        String allUsersResponse = response.getBody();
        System.out.println("All Users: " + allUsersResponse);


    }

    public String saveUser() {
        if (sessionId == null) {
            System.out.println("No sessionId available");
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        User user = new User(3L, "James", "Brown", (byte) 30);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.POST, requestEntity, String.class);
        String partOne = response.getBody();
        System.out.println("Part One: " + partOne);
        return partOne;
    }

    public String updateUser() {
        if (sessionId == null) {
            System.out.println("No sessionId available");
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        User user = new User(3L, "Thomas", "Shelby", (byte) 30);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(BASE_URL, HttpMethod.PUT, requestEntity, String.class);
            String partTwo = response.getBody();
            System.out.println("Part Two: " + partTwo);
            // Здесь может быть логика обработки ответа
            return partTwo;
        } catch (HttpClientErrorException e) {
            System.out.println("Error updating user: " + e.getMessage());
            return null;
        }
    }


    public String deleteUser() {
        if (sessionId == null) {
            System.out.println("No sessionId available");
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.COOKIE, "JSESSIONID=" + sessionId);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/3", HttpMethod.DELETE, requestEntity, String.class);
        String partThree = response.getBody();
        System.out.println("Part Three: " + partThree);
        return partThree;
    }
}