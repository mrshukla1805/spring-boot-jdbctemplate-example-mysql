package com.bezkoder.spring.jdbc.mysql.service;

import com.bezkoder.spring.jdbc.mysql.model.Key;
import com.bezkoder.spring.jdbc.mysql.repository.KeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class KeyService {

    private static final Logger logger = LoggerFactory.getLogger(KeyService.class);

    @Autowired
    private KeyRepository keyRepository;

    public Key createKey() {
        Key newKey = new Key(UUID.randomUUID().toString());
        if (keyRepository.save(newKey) == 1) {
            logger.info("Created new key: {}", newKey.getId());
            return newKey;
        } else {
            logger.error("Failed to create a new key");
            return null;
        }
    }

    public Optional<Key> getAvailableKey() {
        Optional<Key> key = keyRepository.findAvailableKey();
        key.ifPresent(k -> {
            keyRepository.updateBlockedStatusById(k.getId(), true, LocalDateTime.now());
            logger.info("Blocked key: {}", k.getId());
        });
        if (key.isPresent()) {
            logger.info("Found available key: {}", key.get().getId());
        } else {
            logger.info("No available keys found");
        }
        return key;
    }

    public Optional<Key> getKeyById(String id) {
        Optional<Key> key = keyRepository.findById(id);
        if (key.isPresent()) {
            logger.info("Found key by ID: {}", id);
        } else {
            logger.info("Key not found by ID: {}", id);
        }
        return key;
    }

    public boolean unblockKey(String id) {
        boolean result = keyRepository.unblockKey(id) == 1;
        if (result) {
            logger.info("Unblocked key: {}", id);
        } else {
            logger.info("Failed to unblock key: {}", id);
        }
        return result;
    }

    public boolean deleteKey(String id) {
        boolean result = keyRepository.deleteById(id) == 1;
        if (result) {
            logger.info("Deleted key: {}", id);
        } else {
            logger.info("Failed to delete key: {}", id);
        }
        return result;
    }

    public boolean keepAlive(String id) {
        boolean result = keyRepository.updateKeepAlive(id) == 1;
        if (result) {
            logger.info("Kept alive key: {}", id);
        } else {
            logger.info("Failed to keep alive key: {}", id);
        }
        return result;
    }

    @Scheduled(fixedRate = 60000) // 60 sec
    public void releaseBlockedKeys() {
        int updatedRows = keyRepository.releaseBlockedKeys();
        logger.info("Released blocked keys. Number of keys released: {}", updatedRows);
    }

    @Scheduled(fixedRate = 300000) // 5 min
    public void deleteExpiredKeys() {
        int deletedRows = keyRepository.deleteExpiredKeys();
        logger.info("Deleted expired keys. Number of keys deleted: {}", deletedRows);
    }
}
