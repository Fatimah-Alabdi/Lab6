package com.example.lab6.Controller;

import com.example.lab6.Api.ApiRespons;
import com.example.lab6.Model.Employee;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/employee")
public class EmployeeController {
    ArrayList<Employee> employees = new ArrayList<>();

    @GetMapping("/get")
    public ResponseEntity getAllEmployees() {
        return ResponseEntity.status(200).body(employees);
    }

    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiRespons(msg));
        }
        employees.add(employee);
        return ResponseEntity.status(200).body(new ApiRespons("employee added successfully"));
    }

    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee, @PathVariable int index, Errors errors) {
        if (errors.hasErrors()) {
            String msg = errors.getFieldError().getDefaultMessage();
            return ResponseEntity.status(400).body(new ApiRespons(msg));
        }
        employees.set(index, employee);
        return ResponseEntity.status(200).body(new ApiRespons("employee updated successfully"));
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {
         if (index < 0 || index >= employees.size()) {
            return ResponseEntity.status(404).body(new ApiRespons("Employee not found"));
        }
        employees.remove(index);
        return ResponseEntity.status(200).body(new ApiRespons("Employee deleted successfully"));
    }

    @GetMapping("/search/{position}")
    public ResponseEntity searchEmployee(@PathVariable String position) {
        ArrayList<Employee> searchemployees = new ArrayList<>();
        for (Employee employee : employees) {
            if (position.equalsIgnoreCase("supervisor")||position.equalsIgnoreCase("coordinator") && employee.getPosition().equalsIgnoreCase(position) ) {
                searchemployees.add(employee);
            }

        }
        if(searchemployees.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiRespons("employee not found"));
        }
                        return ResponseEntity.status(200).body(searchemployees);

    }

    @GetMapping("/searchByAge/{minage}/{maxAge}")
    public ResponseEntity getEmployeeByage(@PathVariable int maxAge, @PathVariable int minage) {
        ArrayList<Employee> searchemployeesByage = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minage && employee.getAge() <= maxAge) {
                searchemployeesByage.add(employee);

            }

        }
        if (searchemployeesByage.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiRespons("employee not found"));
        }
        return ResponseEntity.status(200).body(searchemployeesByage);
    }

    @PutMapping("/apply/{id}")
    public ResponseEntity ApplyForAnnualLeave(@PathVariable String id) {

        for (Employee emp : employees) {
            if (emp.getId().equals(id)) {
                if (emp.isOnLeave() == false && emp.getAnnualLeave() >= 1) {
                    emp.setAnnualLeave(emp.getAnnualLeave() - 1);
                    emp.setOnLeave(true);
                   

                }
            }
        }
        if(employees.isEmpty()) {
            return ResponseEntity.status(400).body(new ApiRespons("employee not found"));
        }
         return ResponseEntity.status(200).body(new ApiRespons("employee applied successfully"));

    }

    @GetMapping("/getemp")
    public ResponseEntity getEmployeesWithNoAnnualLeave() {
        ArrayList<Employee> employeesWithNoAnnualLeave = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAnnualLeave() == 0) {
                employeesWithNoAnnualLeave.add(employee);
            }
        }
        if(employeesWithNoAnnualLeave.isEmpty()){
        return ResponseEntity.status(400).body(new ApiRespons("No employees found with zero annual leave remaining"));
        }
          return ResponseEntity.status(200).body(employeesWithNoAnnualLeave);

    }

    @PutMapping("/promote/{sId}/{id}")
    public ResponseEntity promoteEmployee(@PathVariable String sId, @PathVariable String id) {

        for (Employee employee : employees) {
            if (employee.getId().equals(sId)) {
                if (employee.getPosition().equalsIgnoreCase("supervisor")) {
                    for (Employee emp : employees) {
                        if (emp.getId().equals(id)) {
                            if (emp.getPosition().equalsIgnoreCase("coordinator") && emp.getAge() >= 30 && !emp.isOnLeave()) {
                                emp.setPosition("supervisor");
                                return ResponseEntity.status(200).body(new ApiRespons("Employee promoted successfully"));
                            } else {
                                return ResponseEntity.status(400).body(new ApiRespons("Employee does not meet promotion criteria"));
                            }
                        }
                    }
                    return ResponseEntity.status(400).body(new ApiRespons("Employee not found"));
                } else {
                    return ResponseEntity.status(400).body(new ApiRespons("Supervisor not found"));
                }
            }
        }
        return ResponseEntity.status(400).body(new ApiRespons("Supervisor not found"));
    }


}








