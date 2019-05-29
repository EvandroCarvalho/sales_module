package br.com.salesModule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.salesModule.error.ItemsNotFound;
import br.com.salesModule.model.User;
import br.com.salesModule.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(value = "Endpoints to manage user")
@RequestMapping(path = "v1/users_accounts")
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    @Autowired
    private final UserRepository userRepository;

    @PostMapping
    @ApiOperation(value = "Save user", response = User.class)
    public ResponseEntity<User> save(@RequestBody User userRequest) {
        log.info("Request: " + userRequest.toString());
        User user = userRepository.save(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PutMapping
    @ApiOperation(value = "Update values of an attribute", response = User.class)
    public ResponseEntity<User> update(@RequestBody User userRequest) {
        User user = userRepository.save(userRequest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping(path = "/{id}")
    @ApiOperation(value = "Delete item available", response = ResponseEntity.class)
    public ResponseEntity<HttpStatus> delete(@PathVariable(value = "id") Long id) throws ItemsNotFound {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            throw new ItemsNotFound("Not found by id: " + id);
        }
    }

    @GetMapping
    @ApiOperation(value = "List all user available, paged and/or ordered", response = User[].class)
    public ResponseEntity<Page<User>> listAll(Pageable page) throws ItemsNotFound {
        Page<User> listUser = userRepository.findAll(page);
        if (listUser.isEmpty()) {
            throw new ItemsNotFound("Not found users");
        } else {
            return ResponseEntity.ok(listUser);
        }
    }

    @GetMapping(path = "/{id}")
    @ApiOperation(value = "Find user by id", response = User.class)
    public ResponseEntity<User> findById(@PathVariable(value = "id") Long id) throws ItemsNotFound {
        User user = userRepository
                .findById(id)
                .orElseThrow(() -> new ItemsNotFound("Not found by id: " + id));
        return ResponseEntity.ok(user);
    }

    @GetMapping(path = "/")
    @ApiOperation(value = "Find user by username", response = User.class)
    public ResponseEntity<User> findById(@RequestParam(value = "username") String username) throws ItemsNotFound {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ItemsNotFound("Not found by username: " + username));
        return ResponseEntity.ok(user);
    }
}
