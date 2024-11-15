package com.parquesoftti.tc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sessions")
public class SimulationRestController {

        @PostMapping()
        public ResponseEntity<String> payment(){
            return ResponseEntity.ok("Hola mundo");
        }

        @PutMapping()
        public ResponseEntity<String> reversePayment(){
            return null;
        }





}
