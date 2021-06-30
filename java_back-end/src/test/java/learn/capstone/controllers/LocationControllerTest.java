package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.domain.LocationService;
import learn.capstone.domain.Result;
import learn.capstone.domain.ResultType;
import learn.capstone.models.Location;
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
public class LocationControllerTest {

    @MockBean
    LocationService service;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        List<Location> locations = List.of(
                new Location(1, "St.Paul, MN"),
                new Location(2, "Minneapolis, MN")
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(locations);

        when(service.findAll()).thenReturn(locations);

        mvc.perform(get("/api/location"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Location expected = new Location(0, "St.Cloud,MN");
        Location actual = new Location(1, "St.Cloud,MN");

        Result<Location> result = new Result<>();
        result.setPayload(actual);
        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = post("/api/location")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);

        mvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    // error giving null pointer
    @Test
    void shouldNotAddNull() throws Exception {
        Location toAdd = new Location(0, " ");

        Result<Location> result = new Result<>();
        result.addMessage("invalid name", ResultType.INVALID);

        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(toAdd);

        when(service.add(toAdd)).thenReturn(result);

        var request = post("/api/location")
                .contentType("application/json")
                .accept("application/json")
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        Location expected = new Location(1, "St.Cloud,MN");

        Result<Location> result = new Result<>();
        result.setPayload(expected);

        when(service.update(expected)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = put("/api/location/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldNotUpdate() throws Exception {
        Location expected = new Location(1000, "St.Cloud,MN");

        Result<Location> result = new Result<>();
        result.addMessage("update failed", ResultType.INVALID);

        when(service.update(expected)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = put("/api/location/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldDelete() throws Exception {
        Location expected = new Location(1, "St.Cloud,MN");

        Result<Location> result = new Result<>();
        result.isSuccess();

        when(service.deleteById(expected.getLocation_id())).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = delete("/api/location/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldNotDelete() throws Exception {
        Location expected = new Location(1000, "St.Cloud,MN");

        Result<Location> result = new Result<>();
        result.addMessage("delete failed", ResultType.INVALID);

        when(service.deleteById(expected.getLocation_id())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = delete("/api/location/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }
}
