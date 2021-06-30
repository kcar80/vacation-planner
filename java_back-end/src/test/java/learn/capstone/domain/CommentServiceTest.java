package learn.capstone.domain;

import learn.capstone.data.CommentRepository;
import learn.capstone.models.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CommentServiceTest {

    @Autowired
    CommentService service;

    @MockBean
    CommentRepository repository;

    @Test
    void shouldFindById() {
        Comment comment = makeComment();
        when(repository.findById(1)).thenReturn(comment);
        Comment actual = service.findById(1);
        assertEquals(comment, actual);
    }

    @Test
    void shouldNotAddWhenInvalid() {
        Comment comment = makeComment();
        comment.setCommentId(0);
        comment.setText(null);
        Result<Comment> result = service.add(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldAddWhenValid() {
        Comment comment = makeComment();
        Comment com = makeComment();
        com.setCommentId(0);

        when(repository.add(com)).thenReturn(comment);
        Result<Comment> result = service.add(com);
        assertEquals(ResultType.SUCCESS, result.getType());

        assertEquals(comment, result.getPayload());
    }

    @Test
    void shouldNotUpdateInvalid() {
        Comment comment = makeComment();
        Comment expected = makeComment();
        expected.setCommentId(0);
        Comment local = new Comment();
        local.setCommentId(1);
        local.setText(null);

        when(repository.add(expected)).thenReturn(comment);

        when(repository.update(comment)).thenReturn(false);
        Result<Comment> result = service.update(comment);
        assertEquals(ResultType.INVALID, result.getType());
    }

    @Test
    void shouldUpdateValid() {
        Comment comment = makeComment();
        Comment expected = makeComment();
        expected.setCommentId(0);
        Comment com = new Comment();
        com.setCommentId(1);
        com.setText("Updated Test");
        com.setUserId(1);
        com.setVacationId(1);

        when(repository.add(expected)).thenReturn(comment);

        when(repository.update(com)).thenReturn(true);
        Result<Comment> result = service.update(com);
        assertEquals(ResultType.SUCCESS, result.getType());
    }

    @Test
    void shouldNotDelete() {
        Comment comment = makeComment();
        Comment expected = makeComment();
        expected.setCommentId(0);

        when(repository.add(comment)).thenReturn(expected);

        when(repository.deleteById(10)).thenReturn(false);
        boolean result = service.deleteById(10);
        assertFalse(result);
    }

    @Test
    void shouldDelete() {
        Comment comment = makeComment();
        Comment expected = makeComment();
        expected.setCommentId(0);

        when(repository.add(comment)).thenReturn(expected);

        when(repository.deleteById(1)).thenReturn(true);
        boolean result = service.deleteById(1);
        assertTrue(result);
    }

    private Comment makeComment() {
        Comment comment = new Comment();
        comment.setText("Test Text");
        comment.setUserId(1);
        comment.setVacationId(1);
        return comment;
    }
}
