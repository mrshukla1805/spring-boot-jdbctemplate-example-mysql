package com.bezkoder.spring.jdbc.mysql.controller;

import com.bezkoder.spring.jdbc.mysql.model.Key;
import com.bezkoder.spring.jdbc.mysql.service.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class KeyController {

    @Autowired
    KeyService keyService;

    @PostMapping("/generateKey")
    public ResponseEntity<Key> generateKey() {
        Key newKey = keyService.createKey();
        if (newKey != null) {
            return new ResponseEntity<>(newKey, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/keys")
    public ResponseEntity<Map<String, String>> getAvailableKey() {
        Optional<Key> key = keyService.getAvailableKey();
        if (key.isPresent()) {
            Map<String, String> response = new HashMap<>();
            response.put("keyId", key.get().getId());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/keys/{id}")
    public ResponseEntity<Key> getKeyById(@PathVariable("id") String id) {
        Optional<Key> key = keyService.getKeyById(id);
        if (key.isPresent()) {
            return new ResponseEntity<>(key.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/keys/{id}")
    public ResponseEntity<Void> unblockKey(@PathVariable("id") String id) {
        if (keyService.unblockKey(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/keys/{id}")
    public ResponseEntity<Void> deleteKey(@PathVariable("id") String id) {
        if (keyService.deleteKey(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/keepalive/{id}")
    public ResponseEntity<Void> keepAlive(@PathVariable("id") String id) {
        if (keyService.keepAlive(id)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
