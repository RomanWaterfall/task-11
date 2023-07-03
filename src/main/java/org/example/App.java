package org.example;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class App {
    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        restTemplate.getMessageConverters().add(converter);
        String url = "http://94.198.50.185:7081/api/users";
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        String userList = response.getBody();
        System.out.println("User List: " + userList);

        Logic logic = new Logic(); // Создание экземпляра класса Logic
        String sessionId = logic.getSessionId(); // Получение sessionId
        if (sessionId == null) {
            System.out.println("No sessionId available");
            return;
        }

        logic.getAllUsers();

        String saveUserResult = logic.saveUser();
        if (saveUserResult == null) {
            System.out.println("Error saving user");
            return;
        }

        String updateUserResult = logic.updateUser();
        if (updateUserResult == null) {
            System.out.println("Error updating user");
            return;
        }

        String deleteUserResult = logic.deleteUser();
        if (deleteUserResult == null) {
            System.out.println("Error deleting user");
            return;
        }

        String finalCode =
                saveUserResult.substring(0, 6) +
                updateUserResult.substring(0, 6) +
                deleteUserResult.substring(0, 6);
        System.out.println("Final Code: " + finalCode);
    }

}
