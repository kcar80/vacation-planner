package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.data.VacationRepository;
import learn.capstone.domain.Result;
import learn.capstone.domain.ResultType;
import learn.capstone.domain.VacationService;
import learn.capstone.models.Comment;
import learn.capstone.models.Vacation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VacationControllerTest {

    @MockBean
    VacationService service;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception{
        List<Vacation> vacations = List.of(
            new Vacation(1, "Hiking trip", 3),
              new Vacation(2, "Resort trip", 1)
        );
        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(vacations);

        when(service.findAll()).thenReturn(vacations);

        mvc.perform(get("/api/vacation"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
       Vacation expected= new Vacation(0, "Hiking trip", 1);
        Vacation actual =new Vacation(1, "Hiking trip", 1);


        Result<Vacation> result = new Result<>();
        result.setPayload(actual);
        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = post("/api/vacation")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldNotAddNull() throws Exception {
        Vacation toAdd = new Vacation(0, "", 1);

        Result<Vacation> result = new Result<>();
        result.addMessage("invalid name", ResultType.INVALID);

        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(toAdd);

        when(service.add(toAdd)).thenReturn(result);

        var request = post("/api/vacation")
                .contentType("application/json")
                .accept("application/json")
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        Vacation expected = new Vacation(1, "Awesome", 1);

        Result<Vacation> result = new Result<>();
        result.setPayload(expected);

        when(service.update(expected)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = put("/api/vacation/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

}