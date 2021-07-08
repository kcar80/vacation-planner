package learn.capstone.data;

import learn.capstone.models.Comment;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository {
    List<Comment> findAll();

    Comment findById(int commentId);

    List<Comment> findByVacationId(int vacationId);

    Comment add(Comment comment);

    boolean update(Comment comment);

    @Transactional
    boolean deleteById(int commentId);
}
