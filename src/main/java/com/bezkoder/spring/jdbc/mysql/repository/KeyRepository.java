package com.bezkoder.spring.jdbc.mysql.repository;

import com.bezkoder.spring.jdbc.mysql.model.Key;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KeyRepository {

    int save(Key key);

    List<Key> findAll();

    Optional<Key> findById(String id);

    Optional<Key> findAvailableKey();

    int updateKeepAlive(String id);

    int unblockKey(String id);

    int deleteById(String id);

    int releaseBlockedKeys();

    int deleteExpiredKeys();

    int updateBlockedStatusById(String id, boolean isBlocked, LocalDateTime blockedAt);

}
