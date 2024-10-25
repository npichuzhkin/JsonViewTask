package com.npichuzhkin.JsonViewTask.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.npichuzhkin.JsonViewTask.dto.OrderDTO;
import com.npichuzhkin.JsonViewTask.dto.UserDTO;
import com.npichuzhkin.JsonViewTask.models.Order;
import com.npichuzhkin.JsonViewTask.models.User;
import com.npichuzhkin.JsonViewTask.services.UserService;
import com.npichuzhkin.JsonViewTask.views.Views;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @JsonView(Views.GeneralUserInformation.class)
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<User> users = userService.readAll();
        List<UserDTO> usersDTO = new LinkedList<>();

        for (User user: users) {
            UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), null);
            usersDTO.add(userDTO);
        }

        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    @JsonView(Views.DetailedUserInformation.class)
    public ResponseEntity<UserDTO> getUserById(@PathVariable UUID id){
        User user = userService.readOne(id);
        UserDTO userDTO = new UserDTO(user.getId(), user.getName(), user.getEmail(), null);
        List<Order> orders = user.getOrders();
        List<OrderDTO> ordersDTO = new LinkedList<>();

        for (Order order: orders) {
            OrderDTO orderDTO = new OrderDTO(order.getId(), order.getProductName(), order.getAmount());
            ordersDTO.add(orderDTO);
        }

        userDTO.setOrders(ordersDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid UserDTO userDTO) {
        User newUser = new User(userDTO.getName(), userDTO.getEmail());
        userService.create(newUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@PathVariable UUID id, @RequestBody @Valid UserDTO userDTO) {
        User updatedUser = new User(userDTO.getName(), userDTO.getEmail());
        userService.update(id, updatedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable UUID id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
