package enterprise.hibisco.hibiscows.controller;

import enterprise.hibisco.hibiscows.entities.User;
import enterprise.hibisco.hibiscows.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService service;

    @GetMapping
    public List<User> getUsers() { return service.getUsers(); }
}
