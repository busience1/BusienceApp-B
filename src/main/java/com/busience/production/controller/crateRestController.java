package com.busience.production.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busience.production.service.CrateService;
import com.busience.tablet.dto.CrateDto;

@RestController("crateRestController")
@RequestMapping("crateRest")
public class crateRestController {
	
	@Autowired
	CrateService crateService;
	
	@GetMapping("/crateSelect")
	public List<CrateDto> crateSelectDao(CrateDto crateDto) {
		return crateService.crateSelectDao(crateDto);
	}
	
	@PostMapping("/crateSave")
	public int crateSaveDao(@RequestBody List<CrateDto> crateDtoList) {
		return crateService.crateSave(crateDtoList);
	}
	
	@PostMapping("/crateUpdate")
	public int crateUpdateDao(CrateDto crateDto) {
		return crateService.crateUpdateDao(crateDto);
	}

}
