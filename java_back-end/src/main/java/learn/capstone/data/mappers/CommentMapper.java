package learn.capstone.data.mappers;

import learn.capstone.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setCommentId(resultSet.getInt("comment_id"));
        comment.setText(resultSet.getString("text"));
        comment.setUserId(resultSet.getInt("user_id"));
        comment.setVacationId(resultSet.getInt("vacation_id"));
        return comment;
    }
}