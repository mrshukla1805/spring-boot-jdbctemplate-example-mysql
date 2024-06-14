package com.bezkoder.spring.jdbc.mysql.repositoryImpl;

import com.bezkoder.spring.jdbc.mysql.model.Key;
import com.bezkoder.spring.jdbc.mysql.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class KeyRepositoryImpl implements KeyRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public KeyRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Key> keyRowMapper = new RowMapper<Key>() {
        @Override
        public Key mapRow(ResultSet rs, int rowNum) throws SQLException {
            Key key = new Key(rs.getString("id"));
            key.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            key.setBlockedAt(rs.getTimestamp("blocked_at") != null ? rs.getTimestamp("blocked_at").toLocalDateTime() : null);
            key.setKeptAliveAt(rs.getTimestamp("kept_alive_at").toLocalDateTime());
            key.setBlocked(rs.getBoolean("is_blocked"));
            return key;
        }
    };

    public int save(Key key) {
        String sql = "INSERT INTO keysTable (id, created_at, blocked_at, kept_alive_at, is_blocked) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, key.getId(), key.getCreatedAt(), key.getBlockedAt(), key.getKeptAliveAt(), key.isBlocked());
    }

    public List<Key> findAll() {
        return null;
    };

    public Optional<Key> findById(String id) {
        try {
            String sql = "SELECT * FROM keysTable WHERE id = ?";
            Key key = jdbcTemplate.queryForObject(sql, keyRowMapper, id);
            return Optional.ofNullable(key);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Key> findAvailableKey() {
        try {
            String sql = "SELECT * FROM keysTable WHERE is_blocked = FALSE AND created_at > NOW() - INTERVAL 5 MINUTE AND kept_alive_at > NOW() - INTERVAL 5 MINUTE ORDER BY created_at LIMIT 1";
            Key key = jdbcTemplate.queryForObject(sql, keyRowMapper);
            return Optional.ofNullable(key);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public int updateKeepAlive(String id) {
        String sql = "UPDATE keysTable SET kept_alive_at = NOW() WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int unblockKey(String id) {
        String sql = "UPDATE keysTable SET is_blocked = FALSE, blocked_at = NULL WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int deleteById(String id) {
        String sql = "DELETE FROM keysTable WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    public int releaseBlockedKeys() {
        String sql = "UPDATE keysTable SET is_blocked = FALSE, blocked_at = NULL WHERE is_blocked = TRUE AND blocked_at <= NOW() - INTERVAL 60 SECOND";
        return jdbcTemplate.update(sql);
    }

    public int deleteExpiredKeys() {
        String sql = "DELETE FROM keysTable WHERE created_at <= NOW() - INTERVAL 5 MINUTE AND kept_alive_at <= NOW() - INTERVAL 5 MINUTE";
        return jdbcTemplate.update(sql);
    }

    public int updateBlockedStatusById(String id, boolean isBlocked, LocalDateTime blockedAt) {
        String sql = "UPDATE keysTable SET is_blocked = ?, blocked_at = ? WHERE id = ?";
        return jdbcTemplate.update(sql, isBlocked, blockedAt, id);
    }

}
