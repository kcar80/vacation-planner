package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.domain.Result;
import learn.capstone.domain.UserService;
import learn.capstone.models.Location;
import learn.capstone.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    UserService service;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        List<User> allUsers = List.of(
                new User(1, "test", "user", "number", "one", true),
                new User(2, "test", "user", "number", "two", false)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(allUsers);

        when(service.findAll()).thenReturn(allUsers);
;
        mvc.perform(get("/api/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);

        Result<User> result = new Result<>();
        result.setPayload(user);
        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(user);
        String expectedJson = jsonMapper.writeValueAsString(setUser);

        var request = post("/api/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldUpdate() throws Exception {
        User user = new User(0, "test", "test", "test", "test", false);
        User setUser = new User(1, "test", "test", "test", "test", false);

        Result<User> result = new Result<>();
        result.setPayload(user);

        when(service.update(setUser)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(setUser);

        var request = put("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldDelete() throws Exception {
        User setUser = new User(1, "test", "test", "test", "test", false);

        Result<User> result = new Result<>();

        when(service.deleteById(setUser.getUserId())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(setUser);

        var request = delete("/api/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }
}