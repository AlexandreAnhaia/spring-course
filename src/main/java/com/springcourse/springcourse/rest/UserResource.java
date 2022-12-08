package com.springcourse.springcourse.rest;

import com.springcourse.springcourse.DTO.*;
import com.springcourse.springcourse.domain.Request;
import com.springcourse.springcourse.domain.User;
import com.springcourse.springcourse.model.PageModel;
import com.springcourse.springcourse.model.PageRequestModel;
import com.springcourse.springcourse.security.JwtManager;
import com.springcourse.springcourse.service.RequestService;
import com.springcourse.springcourse.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "users")
public class UserResource {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtManager jwtManager;

    @Secured({"ROLE_ADMINISTRATOR"})
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSaveDTO userDTO) {
        User userToSave = userDTO.transformToUser();
        User createdUser = userService.save(userToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO userDTO) {
        User user = userDTO.transformToUser();
        user.setId(id);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<PageModel<User>> listAll(@RequestParam Map<String, String> params) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<User> pm = userService.listAllOnLazyModel(pr);
        return ResponseEntity.ok(pm);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> login(@RequestBody @Valid UserLoginDTO userLoginDTO) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        org.springframework.security.core.userdetails.User userSpring = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        String email = userSpring.getUsername();
        List<String> roles = userSpring.getAuthorities().stream().map(authority -> authority.getAuthority()).collect(Collectors.toList());
        UserLoginResponseDTO userLoginResponseDTO = jwtManager.createToken(email, roles);
        return ResponseEntity.ok(userLoginResponseDTO);
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<PageModel<Request>> listAllRequestById(
            @PathVariable(name = "id") Long id,
            @RequestParam Map<String, String> params) {
        PageRequestModel pr = new PageRequestModel(params);
        PageModel<Request> pm = requestService.listAllByOwnerIdOnLazyModel(id, pr);
        return ResponseEntity.ok(pm);
    }

    @Secured({"ROLE_ADMINISTRATOR"})
    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(
            @PathVariable(name = "id") Long id,
            @RequestBody @Valid UserUpdateRoleDTO userDTO) {
        User user = new User();
        user.setId(id);
        user.setRole(userDTO.getRole());
        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }

}
