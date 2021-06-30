package learn.capstone.data;

import learn.capstone.models.Comment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class CommentJdbcTemplateRepositoryTest {

    final static int NEXT_ID = 4;

    @Autowired
    CommentJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Comment> comments = repository.findAll();
        assertNotNull(comments);
        assertTrue(comments.size() >= 3 && comments.size() <= 5);
    }

    @Test
    void shouldFindById() {
        Comment comment = repository.findById(1);
        assertEquals(1, comment.getComment_id());
    }

    @Test
    void shouldAdd() {
        Comment comment = makeComment();
        Comment actual = repository.add(comment);
        assertNotNull(actual);
        assertEquals(NEXT_ID, actual.getComment_id());
    }

    @Test
    void shouldUpdate() {
        Comment comment = makeComment();
        comment.setComment_id(1);
        assertTrue(repository.update(comment));
        comment.setComment_id(13);
        assertFalse(repository.update(comment));
    }

    @Test
    void shouldDelete() {
        assertTrue(repository.deleteById(2));
        assertFalse(repository.deleteById(2));
    }

    private Comment makeComment() {
        Comment comment = new Comment();
        comment.setText("Test Text");
        comment.setUser_id(1);
        comment.setVacation_id(1);
        return comment;
    }
}
