package com.npichuzhkin.JsonViewTask;

import com.npichuzhkin.JsonViewTask.controllers.UsersController;
import com.npichuzhkin.JsonViewTask.models.Order;
import com.npichuzhkin.JsonViewTask.models.User;
import com.npichuzhkin.JsonViewTask.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(new UsersController(userService)).build();
    }
    @Test
    public void getAllUsersShouldReturnUserList() throws Exception {
        User user = new User(UUID.randomUUID(), "Tom", "tom@example.com");
        when(userService.readAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Tom"))
                .andExpect(jsonPath("$[0].email").value("tom@example.com"));
    }

    @Test
    public void getUserByIdShouldReturnUser() throws Exception {
        UUID userId = UUID.randomUUID();
        List<Order> orders = Collections.singletonList(new Order());
        User user = new User(userId, "Tom", "tom@example.com", orders);
        when(userService.readOne(userId)).thenReturn(user);

        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Tom"))
                .andExpect(jsonPath("$.email").value("tom@example.com"));
    }

    @Test
    public void registerShouldReturnOkStatus() throws Exception {

        mockMvc.perform(post("/api/v1/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Kate\", \"email\":\"kate@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void updateShouldReturnOkStatus() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(patch("/api/v1/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated\", \"email\":\"updated@example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteShouldReturnOkStatus() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
