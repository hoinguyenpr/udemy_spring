package com.dpm.controller;
//package com.mock2.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.propertyeditors.StringTrimmerEditor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.InitBinder;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.mock2.entity.Department;
//import com.mock2.service.DepartmentService;
//
//@RestController
//@RequestMapping()
//public class DepartmentAPI {
//	
//	@Autowired
//	DepartmentService departmentService;
//
//	@GetMapping(value = "/all-department")
//    public ResponseEntity<List<Department>> viewAllDepartment() {
//
//        List<Department> allDepartment = departmentService.getAllDepartment();
//        return ResponseEntity.status(HttpStatus.OK).body(allDepartment);
//    }
//	
//}
