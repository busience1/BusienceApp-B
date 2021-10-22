package com.busience.standard.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.busience.common.service.DtlService;

@Controller
public class standardController {
	
	@Autowired
	DtlService dtlService;
	
	@GetMapping("/userManage")
	public String userManage(Model model) {
		
		//사용자 타입
		int userType = 1;
		model.addAttribute("userTypeList", dtlService.getAlldtl(userType));
		
		//사업장
		int company = 2;
		model.addAttribute("companyList", dtlService.getAlldtl(company));
		
		//부서
		int dept = 3;
		model.addAttribute("deptList", dtlService.getAlldtl(dept));
		
		//메뉴명
		model.addAttribute("pageName", "사용자 관리");

		return "standard/userManage";
	}
	
	@GetMapping("itemManage")
	public String itemManage(Model model) {

		//사업장
		int company = 2;
		model.addAttribute("companyList", dtlService.getAlldtl(company));
		
		//단위
		int unit = 4;
		model.addAttribute("unitList", dtlService.getAlldtl(unit));
		
		//자재분류
		int mtrlClsfc = 5;
		model.addAttribute("mtrlClsfcList", dtlService.getAlldtl(mtrlClsfc));
	
		//품목분류1
		int itemClsfc1 = 6;
		model.addAttribute("itemClsfc1List", dtlService.getAlldtl(itemClsfc1));
	
		//품목분류2
		int itemClsfc2 = 7;
		model.addAttribute("itemClsfc2List", dtlService.getAlldtl(itemClsfc2));
		
		//재질
		int material = 8;
		model.addAttribute("materialList", dtlService.getAlldtl(material));
		
		//품목상태
		int itemStatus = 9;
		model.addAttribute("itemStatusList", dtlService.getAlldtl(itemStatus));
		
		//기본창고
		int basicWarehouse = 10;
		model.addAttribute("basicWarehouseList", dtlService.getAlldtl(basicWarehouse));

		//페이지명
		model.addAttribute("pageName", "품목 정보 관리");
		
		return "standard/itemManage";
	}
	
	@GetMapping("customerManage")
	public String customer(Model model){

		// 납품조건
		int paymentMethod = 15;
		model.addAttribute("paymentMethodList", dtlService.getAlldtl(paymentMethod));
		
		// 페이지명
		model.addAttribute("pageName", "거래처 관리");
		
		return "standard/customerManage";
	}
	
	@GetMapping("machineManage")
	public String machineManage(Model model) throws SQLException {
		
		//사업장
		int company = 2;
		model.addAttribute("companyList", dtlService.getAlldtl(company));
		
		//설비상태
		int equipmentStatus = 12;
		model.addAttribute("equipmentStatusList", dtlService.getAlldtl(equipmentStatus));
		
		model.addAttribute("pageName", "설비 정보 관리");
		
		return "standard/machineManage";
	}
	
	@GetMapping("spareTypeManage")
	public String list(Model model) throws SQLException {
		model.addAttribute("pageName", "스페어 파트 관리");
		return "standard/spareTypeManage";
	}
	
	@GetMapping("defectManage")
	public String defect(Model model){
		model.addAttribute("pageName", "불량 정보 관리");
		return "standard/defectManage";
	}
	
	@GetMapping("BOM")
	public String BOM(Model model) {
		
		// 자재분류
		int mtrlClsfc = 5;
		model.addAttribute("mtrlClsfcList", dtlService.getAlldtl(mtrlClsfc));
		model.addAttribute("pageName", "BOM 입력");
		
		return "standard/BOM";
	}
	
	@GetMapping("BOMListMaster")
	public String BOMListMaster(Model model) {
		
		// 자재분류
		int mtrlClsfc = 5;
		model.addAttribute("mtrlClsfcList", dtlService.getAlldtl(mtrlClsfc));
		model.addAttribute("pageName", "BOM 조회");
		
		return "standard/BOMList/BOMListMaster";
	}
	
	@GetMapping("packagingManage")
	public String packagingManage(Model model) {
		model.addAttribute("pageName", "포장 규격 관리");
		return "standard/packagingManage";
	}
	
	@GetMapping("routingInput")
	public String routingInput(Model model) {
		model.addAttribute("pageName", "라우팅 등록");
		return "standard/routingInput";
	}
	
	@GetMapping("matmodelmapping")
	public String matmodelmapping(Model model) {
		model.addAttribute("pageName", "원자재 모델 Mapping");
		return "standard/matmodelmapping";
	}
}
