package ru.clevertec.ecl.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.domain.user.UserService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> findById(@PathVariable @Positive Integer id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<UserReadDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAll(pageable));
    }
}
