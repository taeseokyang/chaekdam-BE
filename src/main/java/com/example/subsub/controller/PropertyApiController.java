package com.example.subsub.controller;

import com.example.subsub.domain.Property;
import com.example.subsub.dto.PropertyDTO;
import com.example.subsub.dto.request.AddPropertyRequest;
import com.example.subsub.dto.request.UpdatePropertyRequest;
import com.example.subsub.dto.response.PropertyResponse;
import com.example.subsub.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/property")
@RequiredArgsConstructor
public class PropertyApiController {

    private final PropertyService propertyService;

    @PostMapping
    public ResponseEntity<PropertyDTO> save(@RequestBody AddPropertyRequest request) {
        Property savedProperty = propertyService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(PropertyDTO.toPropertyDto(savedProperty));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Integer id) {
        propertyService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<PropertyResponse>> findAll() {
        List<PropertyResponse> properties = propertyService.findAll()
                .stream()
                .map(PropertyResponse::new)
                .toList();

        return ResponseEntity.ok().body(properties);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PropertyDTO> update(@PathVariable Integer id, @RequestBody UpdatePropertyRequest request) {
        Property updatedProperty = propertyService.update(id, request);
        return ResponseEntity.ok().body(PropertyDTO.toPropertyDto(updatedProperty));
    }

    @GetMapping("/top5")
    public ResponseEntity<List<PropertyResponse>> getTop5Properties(){
        List<PropertyResponse> top5Properties = propertyService.getTop5PropertiesOrderedByExpiredAt()
                .stream()
                .map(PropertyResponse::new)
                .toList();

        if (top5Properties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok().body(top5Properties);
    }
}
