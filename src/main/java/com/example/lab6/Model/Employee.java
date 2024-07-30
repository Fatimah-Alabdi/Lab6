package com.example.lab6.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "Id should not be empty")
    @Size(min = 3,message = "Id must be more than 2 numbers")
    private String id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 5,message = " Name must be more than 4 charactar")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Name must contain only characters")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Must be a valid email format")
    private String email;

    @Pattern(regexp = "^05[0-9]{8}$", message = "Phone number must start with '05' and consist of exactly 10 digits")
    private String phoneNumber;

@NotNull(message = "Age cannot be null")
@Positive(message = "Age must be positive number")
@Min(value = 26)
@Max(value = 70)
    private int age;

    @NotEmpty(message = "Position cannot be empty")
    @Pattern(regexp = "supervisor|coordinator", message = "Position must be either 'supervisor' or 'coordinator' only")
    private String position;

@AssertFalse
    private boolean onLeave;

    @NotNull(message = "Hire date cannot be null")
    @PastOrPresent(message = "Hire date should be a date in the past or the present")
    //@Min(value = 1900, message = "Year must be 1900 or later")
    private Date hireDate;

    @NotNull(message = "annualLeave cannot be null")
   @PositiveOrZero(message = "annualLeave must be positive number")

    private int annualLeave;

}
