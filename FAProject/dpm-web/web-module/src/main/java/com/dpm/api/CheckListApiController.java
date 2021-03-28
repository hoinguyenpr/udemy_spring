package com.dpm.api;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dpm.constant.Constants;
import com.dpm.dto.CheckListDTO;
import com.dpm.dto.CheckListSettingDTO;
import com.dpm.entity.CheckList;
import com.dpm.entity.ProductLot;
import com.dpm.service.CheckListSevice;
import com.dpm.service.ProductLotSevice;
@CrossOrigin(origins = "*", maxAge = 3600, allowedHeaders="*")
@RestController
@RequestMapping("/checklist")
public class CheckListApiController {
	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CheckListSevice checkListSevice;

	@Autowired
	private ProductLotSevice lotSevice;

	@GetMapping(path = "/api/{id}")
	public ResponseEntity<Object> get(@PathVariable int id) {

		Optional<CheckList> checklist = checkListSevice.get(id);
		System.out.println(checklist.get());
		if (checklist.isPresent()) {
			return new ResponseEntity<Object>(checklist.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("Not found", HttpStatus.OK);
		}

	}
	@GetMapping(path="/test")
	public String test() {
		return "test-test";
	}
	
	
	@GetMapping(path = "/api/lot-by-type/{id}")
	public ResponseEntity<Object> getProductLotByType(@PathVariable int id) {
		List<ProductLot> productLots = null;
		try {
			if (id == 0) {
				productLots = lotSevice.getAllProductLot();
			} else {
				productLots = lotSevice.getByType(id);
			}

		} catch (Exception e) {
			productLots = new ArrayList<ProductLot>();
		}

		return new ResponseEntity<Object>(productLots, HttpStatus.OK);

	}

	@PostMapping(path = "/api/validate")
	@ResponseBody
	public ResponseEntity<Object> validate(@Valid @ModelAttribute("checklistObject") CheckListDTO dto,
			BindingResult bindingResult) {
		System.out.println(dto.toString());
		List<FieldError> objectErrors = new ArrayList<FieldError>();
		try {
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
			LocalTime time = LocalTime.parse(dto.getTime(), timeFormatter);

		} catch (Exception e) {
			objectErrors.add(new FieldError("checklistObject", "time", "Sai định dạng giờ HH:mm (AM or PM)"));
		}

		try {
			LocalDate date = LocalDate.parse(dto.getDate());
		} catch (Exception e) {
			objectErrors.add(new FieldError("checklistObject", "date", "Sai định dạng ngày yyyy-MM-dd"));
		}

		if (bindingResult.hasErrors()) {
			objectErrors = bindingResult.getFieldErrors().stream().collect(Collectors.toList());

			return ResponseEntity.ok(setDefaultMessage(objectErrors));
		} else {
			return ResponseEntity.ok(objectErrors);
		}
	}

	@GetMapping(path = "/api/setting/{id}")
	public ResponseEntity<Object> setSettingById(@PathVariable int id) {

		String result = checkListSevice.setting(id);
		if (Constants.SUCCESS.equals(result)) {
			Optional<CheckList> checkList = checkListSevice.get(id);
			CheckListSettingDTO setting = checkList.get().toSettingDTO();
			return new ResponseEntity<Object>(setting, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>(result, HttpStatus.OK);
		}

	}

	private List<FieldError> setDefaultMessage(List<FieldError> objectErrors) {
		Locale loc = LocaleContextHolder.getLocale();
		List<FieldError> results = new ArrayList<FieldError>();
		for (FieldError fieldError : objectErrors) {
			System.out.println(fieldError);
			if (Arrays.stream(fieldError.getCodes()).anyMatch("typeMismatch.float"::equals)) {
				String mess = "";
				if (Arrays.stream(fieldError.getCodes()).anyMatch("typeMismatch.brix"::equals)) {
					mess = "validation.typeMismatch.float2";
				} else {
					mess = "validation.typeMismatch.float";
				}
				FieldError f = new FieldError(fieldError.getObjectName(), fieldError.getField(),
						fieldError.getRejectedValue(), fieldError.isBindingFailure(), fieldError.getCodes(),
						fieldError.getArguments(), messageSource.getMessage(mess, null, loc));
				results.add(f);
			} else if (Arrays.stream(fieldError.getCodes()).anyMatch("typeMismatch.ph"::equals)) {
				FieldError f = new FieldError(fieldError.getObjectName(), fieldError.getField(),
						fieldError.getRejectedValue(), fieldError.isBindingFailure(), fieldError.getCodes(),
						fieldError.getArguments(), messageSource.getMessage("validation.checklist.ph", null, loc));
				results.add(f);
			} else if (Arrays.stream(fieldError.getCodes()).anyMatch("typeMismatch.java.lang.Integer"::equals)) {
				FieldError f = new FieldError(fieldError.getObjectName(), fieldError.getField(),
						fieldError.getRejectedValue(), fieldError.isBindingFailure(), fieldError.getCodes(),
						fieldError.getArguments(),
						messageSource.getMessage("validation.typeMismatch.int", null, loc));
				results.add(f);
			} else {
				results.add(fieldError);
			}
		}
		return results;
	}

}
