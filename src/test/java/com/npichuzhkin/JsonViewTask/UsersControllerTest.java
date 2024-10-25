package com.npichuzhkin.JsonViewTask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.npichuzhkin.JsonViewTask.models.User;
import com.npichuzhkin.JsonViewTask.models.Order;
import com.npichuzhkin.JsonViewTask.services.UserService;
import com.npichuzhkin.JsonViewTask.controllers.UsersController;
import com.npichuzhkin.JsonViewTask.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @MockBean
    private UserService userService;

    @InjectMocks
    private UsersController usersController;

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void getAllUsersReturnsListOfUsers() throws Exception {
        List<User> users = Arrays.asList(
                new User("John", "john@example.com"),
                new User("Jane", "jane@example.com")
        );
        when(userService.readAll()).thenReturn(users);

        mockMvc.perform(get("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(users.size()))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Jane"));

        verify(userService, times(1)).readAll();
    }

    @Test
    public void getUserByIdReturnsUser() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User("John", "john@example.com");
        List<Order> orders = new LinkedList<>();
        when(userService.readOne(userId)).thenReturn(user);
        when(user.getOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));

        verify(userService, times(1)).readOne(userId);
    }

    @Test
    public void registerCreatesUser_ReturnsOk() throws Exception {
        UserDTO userDTO = new UserDTO(null, "John", "john@example.com", null);

        mockMvc.perform(post("/api/v1/registration")
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).create(any(User.class));
    }

    @Test
    public void updateUserReturnsOk() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = new UserDTO(null, "John", "john@example.com", null);

        mockMvc.perform(patch("/api/v1/{id}", userId)
                        .content(objectMapper.writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).update(eq(userId), any(User.class));
    }

    @Test
    public void deleteUserReturnsOk() throws Exception {
        UUID userId = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(userId);
    }
}
