package learn.capstone.data.mappers;

import learn.capstone.models.Comment;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CommentMapper implements RowMapper<Comment> {

    @Override
    public Comment mapRow(ResultSet resultSet, int i) throws SQLException {
        Comment comment = new Comment();
        comment.setComment_id(resultSet.getInt("comment_id"));
        comment.setText(resultSet.getString("text"));
        comment.setUser_id(resultSet.getInt("user_id"));
        comment.setVacation_id(resultSet.getInt("vacation_id"));
        return comment;
    }
}