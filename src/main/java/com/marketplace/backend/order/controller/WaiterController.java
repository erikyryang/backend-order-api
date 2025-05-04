package com.marketplace.backend.order.controller;

import com.marketplace.backend.order.entity.WaiterEntity;
import com.marketplace.backend.order.service.WaiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/waiter")
public class WaiterController {

    private final WaiterService waiterService;

    @PostMapping
    public ResponseEntity<WaiterEntity> create(@RequestBody WaiterEntity waiter) {
        WaiterEntity createdWaiter = waiterService.create(waiter);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWaiter);
    }

    @GetMapping
    public ResponseEntity<List<WaiterEntity>> getAllWaiters() {
        List<WaiterEntity> waiters = waiterService.getAllWaiters();
        return new ResponseEntity<>(waiters, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WaiterEntity> getById(@PathVariable Long id) {
        WaiterEntity waiter = waiterService.getWaiterById(id);
        return new ResponseEntity<>(waiter, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WaiterEntity> update(@PathVariable Long id, @Valid @RequestBody WaiterEntity waiter) {
        WaiterEntity updatedWaiter = waiterService.update(id, waiter);
        return new ResponseEntity<>(updatedWaiter, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        waiterService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
