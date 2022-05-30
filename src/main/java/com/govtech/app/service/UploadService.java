package com.govtech.app.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.govtech.app.model.User;
import com.govtech.app.util.exception.CsvInvalidFormatException;
import com.govtech.app.util.exception.UserEntityException;
import com.govtech.app.util.exception.UserEntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService implements IUploadService {

    private final int columnsLength = 2;
    
    @Autowired
    private IUserService userService;

    // Read CSV file and saves to database
    @Transactional
    @Override
    public void parseCsvAndSave(MultipartFile file) throws CsvInvalidFormatException {
        // Simple file validation
        if (file.isEmpty()) return;
        if (!file.getContentType().equals("text/csv")) {
            throw new CsvInvalidFormatException("File must be a .csv extension.");
        }
        
        // Parse csv rows into User entities
        List<User> newUsers = parseCsvToList(file);

        try {
            for (User user: newUsers) {
                // Upsert each user: Update if user exists by name, else insert new user into database
                try {
                    userService.update(user);
                } catch (UserEntityNotFoundException ex) {
                    userService.save(user);
                }
            }

        } catch (UserEntityException ex) {
            throw new CsvInvalidFormatException(ex.getMessage());
        }
    }

    // Read and validates csv file line by line and convert into User entities.
    private List<User> parseCsvToList(MultipartFile file) throws CsvInvalidFormatException {
        try {
            List<User> newUsers = new ArrayList<>();

            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));

            // Skip CSV header
            String line = reader.readLine();
    
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");

                // Validate columns
                if (values.length != columnsLength) {
                    throw new CsvInvalidFormatException("Csv must have exactly " + columnsLength + " columns.");
                }

                // Validate name field
                String name = values[0];
                if (name == "") {
                    throw new CsvInvalidFormatException("Name cannot be empty.");
                }

                // Validate salary field
                BigDecimal salary;
                try {
                    salary = new BigDecimal(values[1]);
                } catch (NumberFormatException ex) {
                    throw new CsvInvalidFormatException(values[1] + " is not a number.");
                }

                // Add entry only if salary >= 0
                if (salary.compareTo(BigDecimal.ZERO) >= 0) {
                    newUsers.add(new User(name, salary));
                }

            }

            return newUsers;

        } catch (IOException ex) {
            throw new CsvInvalidFormatException("Error reading file: " + ex.getMessage());
        }
    }
}
