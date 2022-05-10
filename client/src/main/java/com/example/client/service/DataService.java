package com.example.client.service;

import com.example.client.service.exception.ValidationException;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    public void validateDate(Integer from, Integer to) {

        if (from == null || to == null || from >= to) {
            throw new ValidationException("Incorrect data");
        }
        if (from == 0) {
            throw new ValidationException("The first number must be greater than 0");
        }
        if (to > 50) {
            throw new ValidationException("The last element must be less than or equal to 50");
        }

    }
}
