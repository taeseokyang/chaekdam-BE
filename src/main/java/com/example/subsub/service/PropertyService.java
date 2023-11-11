package com.example.subsub.service;

import com.example.subsub.domain.Property;
import com.example.subsub.domain.Subject;
import com.example.subsub.dto.request.AddPropertyRequest;
import com.example.subsub.dto.request.UpdatePropertyRequest;
import com.example.subsub.repository.PropertyRepository;
import com.example.subsub.repository.SubjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final SubjectRepository subjectRepository;
    private final PropertyRepository propertyRepository;

    public Property save(AddPropertyRequest request) {
        Subject subjectEntity = subjectRepository.findById(request.getSubjectId()).orElseThrow(IllegalArgumentException::new);
        Property property = Property.of(request, subjectEntity);
        return propertyRepository.save(property);
    }

    public void delete(Integer propertyId) {
        propertyRepository.deleteById(propertyId);
    }

    public List<Property> findAll() {
        return propertyRepository.findAll();
    }

    @Transactional
    public Property update(Integer id, UpdatePropertyRequest request) {
        Property savedProperty = propertyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        savedProperty.update(request);
        return savedProperty;
    }

    public List<Property> getTop5PropertiesOrderedByExpiredAt(){
        return propertyRepository.findTop5ByOrderByExpiredAtAsc();
    }
}
