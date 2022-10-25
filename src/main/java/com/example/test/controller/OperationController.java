package com.example.test.controller;

import com.example.test.model.Operation;
import com.example.test.service.OperationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@RestController
@RequestMapping("/test")
@AllArgsConstructor
public class OperationController {

    private OperationService operationService;

    @PostMapping(produces = {"application/json", "application/xml"})
    public ResponseEntity operation(@RequestBody Operation operation) {
        try {
            return ResponseEntity.ok(Map.of(operation.getOperation_name().substring(4), operationService.execute(operation)));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return ResponseEntity.badRequest().body(e.getMessage() + "\n" + e.getCause());
        }

    }

    @PostMapping(value = "/operation/{operation_name}", produces = {"application/json", "application/xml"})
    public ResponseEntity operation(@PathVariable String operation_name, @RequestBody Operation operation) {
        try {
            operation.setOperation_name(operation_name);
            return ResponseEntity.ok(Map.of(operation_name.substring(4), operationService.execute(operation)));
        } catch (IOException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println(e.getClass().getName());
            return ResponseEntity.badRequest().body(e.getMessage() + "\n" + e.getCause());
        }
    }

}
