package Controller;

import model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {


    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        return ResponseEntity.ok("User updated successfully");
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long id) {

        return ResponseEntity.ok("User deleted successfully");
    }
}



