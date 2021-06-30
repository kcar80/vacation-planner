package learn.capstone.domain;

import learn.capstone.data.CommentRepository;
import learn.capstone.models.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

     private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Comment findById(int commentId) {
        return repository.findById(commentId);
    }

   public Result<Comment> add(Comment comment) {
        Result<Comment> result = validate(comment);
        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getCommentId() != 0) {
            result.addMessage("commentId cannot be set for `add` operation", ResultType.INVALID);
            return result;
        }

        comment = repository.add(comment);
        result.setPayload(comment);
        return result;
    }

    public Result<Comment> update(Comment comment) {
        Result<Comment> result = validate(comment);
        if (!result.isSuccess()) {
            return result;
        }

        if (comment.getCommentId() <= 0) {
            result.addMessage("commentId must be set for `update` operation", ResultType.INVALID);
            return result;
        }

        if (!repository.update(comment)) {
            String msg = String.format("commentId: %s, not found", comment.getCommentId());
            result.addMessage(msg, ResultType.NOT_FOUND);
        }

        return result;
    }

    public boolean deleteById(int commentId) {
        return repository.deleteById(commentId);
    }

    private Result<Comment> validate(Comment comment) {
        Result<Comment> result = new Result<>();
        if (comment == null) {
            result.addMessage("comment cannot be null", ResultType.INVALID);
            return result;
        }

        if (Validations.isNullOrBlank(comment.getText())) {
            result.addMessage("description is required", ResultType.INVALID);
        }

        if(Validations.isZeroOrLessThanZero(comment.getUserId())) {
            result.addMessage("user_id needs to be greater than 0", ResultType.INVALID);
        }

        if(Validations.isZeroOrLessThanZero(comment.getVacationId())) {
            result.addMessage("vacation_id needs to be greater than 0", ResultType.INVALID);
        }

        return result;
    }
}
