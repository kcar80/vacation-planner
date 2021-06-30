package learn.capstone.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import learn.capstone.domain.CommentService;
import learn.capstone.domain.Result;
import learn.capstone.domain.ResultType;
import learn.capstone.models.Comment;
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
public class CommentControllerTest {

    @MockBean
    CommentService service;

    @Autowired
    MockMvc mvc;

    @Test
    void shouldGetAll() throws Exception {
        List<Comment> comments = List.of(
                new Comment(1, "Very cool", 1, 1),
                new Comment(2, "Very boring", 1, 2)
        );

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(comments);

        when(service.findAll()).thenReturn(comments);

        mvc.perform(get("/api/comment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedJson));
    }

    @Test
    void shouldAdd() throws Exception {
        Comment expected = new Comment(0, "Awesome", 1, 1);
        Comment actual = new Comment(1, "Awesome", 1, 1);

        Result<Comment> result = new Result<>();
        result.setPayload(actual);
        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);
        String expectedJson = jsonMapper.writeValueAsString(actual);

        var request = post("/api/comment")
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
        Comment toAdd = new Comment(0, "", 1, 1);

        Result<Comment> result = new Result<>();
        result.addMessage("invalid name", ResultType.INVALID);

        when(service.add(any())).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String expectedJson = jsonMapper.writeValueAsString(toAdd);

        when(service.add(toAdd)).thenReturn(result);

        var request = post("/api/comment")
                .contentType("application/json")
                .accept("application/json")
                .content(expectedJson);

        mvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdate() throws Exception {
        Comment expected = new Comment(1, "Awesome", 1, 1);

        Result<Comment> result = new Result<>();
        result.setPayload(expected);

        when(service.update(expected)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = put("/api/comment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldNotUpdate() throws Exception {
        Comment expected = new Comment(1000, "Awesome", 1, 1);

        Result<Comment> result = new Result<>();
        result.addMessage("update failed", ResultType.INVALID);

        when(service.update(expected)).thenReturn(result);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = put("/api/comment/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldDelete() throws Exception {
        Comment expected = new Comment(1, "Awesome", 1, 1);

        Result<Comment> result = new Result<>();
        result.isSuccess();

        when(service.deleteById(expected.getCommentId())).thenReturn(true);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = delete("/api/comment/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }

    @Test
    void shouldNotDelete() throws Exception {
        Comment expected = new Comment(1000, "Awesome", 1, 1);

        Result<Comment> result = new Result<>();
        result.addMessage("delete failed", ResultType.INVALID);

        when(service.deleteById(expected.getCommentId())).thenReturn(false);

        ObjectMapper jsonMapper = new ObjectMapper();
        String jsonIn = jsonMapper.writeValueAsString(expected);

        var request = delete("/api/comment/1000")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonIn);
    }
}
