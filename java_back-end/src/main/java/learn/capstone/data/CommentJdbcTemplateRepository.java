package learn.capstone.data;

import learn.capstone.data.mappers.CommentMapper;
import learn.capstone.models.Comment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CommentJdbcTemplateRepository implements CommentRepository {

    private final JdbcTemplate jdbcTemplate;

    public CommentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Comment> findAll() {
        final String sql = "select comment_id, text, user_id, vacation_id "
                + "from comment limit 1000;";
        return jdbcTemplate.query(sql, new CommentMapper());
    }

    @Override
    @Transactional
    public Comment findById(int commentId) {
        final String sql = "select comment_id, text, user_id, vacation_id "
                + "from comment "
                + "where comment_id = ?;";

        Comment comment = jdbcTemplate.query(sql, new CommentMapper(), commentId).stream()
                .findFirst().orElse(null);

        return comment;
    }

    @Override
    public Comment add(Comment comment) {
        final String sql = "insert into comment (text, user_id, vacation_id) "
                + " value (?,?,?);";

       KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, comment.getText());
            ps.setInt(2, comment.getUserId());
            ps.setInt(3, comment.getVacationId());
            return ps;
        }, keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        comment.setCommentId(keyHolder.getKey().intValue());
        return comment;
    }

    @Override
    public boolean update(Comment comment) {

        final String sql = "update comment set "
                + "text = ?, "
                + "user_id = ?, "
                + "vacation_id = ? "
                + "where comment_id = ?;";

        return jdbcTemplate.update(sql,
                comment.getText(),
                comment.getUserId(),
                comment.getVacationId(),
                comment.getCommentId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int commentId) {
        return jdbcTemplate.update("delete from comment where comment_id = ?;", commentId) > 0;
    }
}
