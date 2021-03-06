package com.busience.common.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.HometaxApiDto;
import com.busience.common.service.HometaxApiService;
import com.busience.standard.dto.CustomerDto;
import com.busience.standard.dto.DefectInfoDto;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.dto.MachineDto;
import com.busience.standard.dto.PaldangPackagingStandardDto;
import com.busience.standard.service.ItemService;
import com.busience.standard.service.PaldangPackagingStandardService;

@RestController
public class popupRestController {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	HometaxApiService hometaxApiService;
	
	@Autowired
	ItemService itemService;
	
	@Autowired
	JdbcTemplate jdbctemplate;
	
	@Autowired
	PaldangPackagingStandardService paldangPackagingService;
	
	//itemPopup
	@GetMapping("/itemPopupSelect")
	public List<ItemDto> itemPopupSelect(
			@RequestParam(value = "item_Word", required = false) String item_Word,
			@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		conn = dataSource.getConnection();
		
		String sql = "";
		
		sql = " select A.PRODUCT_ITEM_CODE,\r\n"
				+ " A.PRODUCT_ITEM_NAME,\r\n"
				+ " A.PRODUCT_INFO_STND_1,\r\n"
				+ " A.PRODUCT_INFO_STND_2,\r\n"
				+ " B.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC,\r\n"
				+ " C.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_1_NAME,\r\n"
				+ " D.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_2_NAME,\r\n"
				+ " E.CHILD_TBL_TYPE PRODUCT_UNIT_NAME,\r\n"
				+ " F.CHILD_TBL_TYPE PRODUCT_MATERIAL_NAME,\r\n"
				+ " A.PRODUCT_UNIT_PRICE\r\n"
				+ " from Product_Info_tbl A\r\n"
				+ " inner join DTL_TBL B on A.PRODUCT_MTRL_CLSFC = B.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL C on A.Product_Item_CLSFC_1 = C.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL D on A.Product_Item_CLSFC_2 = D.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL E on A.Product_Unit = E.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL F on A.Product_Material = F.CHILD_TBL_NO\r\n"
				+ " where (A.PRODUCT_ITEM_CODE like '%"+item_Word+"%' or A.PRODUCT_ITEM_NAME like '%"+item_Word+"%')\r\n"
				+ " and A.PRODUCT_MTRL_CLSFC like '%" + search_value + "%'\r\n"
				+ " and A.PRODUCT_USE_STATUS='1'";
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		List<ItemDto> list = new ArrayList<ItemDto>();

		while (rs.next()) {
			ItemDto data = new ItemDto();

			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_INFO_STND_2(rs.getString("PRODUCT_INFO_STND_2"));
			data.setPRODUCT_MTRL_CLSFC(rs.getString("PRODUCT_MTRL_CLSFC"));
			data.setPRODUCT_UNIT_NAME(rs.getString("PRODUCT_UNIT_NAME"));
			data.setPRODUCT_MATERIAL_NAME(rs.getString("PRODUCT_MATERIAL_NAME"));
			data.setPRODUCT_ITEM_CLSFC_1_NAME(rs.getString("PRODUCT_ITEM_CLSFC_1_NAME"));
			data.setPRODUCT_ITEM_CLSFC_2_NAME(rs.getString("PRODUCT_ITEM_CLSFC_2_NAME"));
			
			data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
			list.add(data);
		}	
	
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//itemPopup
		@GetMapping("/itemPopupSTND")
		public List<ItemDto> itemPopupSTND(
				@RequestParam(value = "item_Word", required = false) String item_Word,
				@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			conn = dataSource.getConnection();
			String sql = "";
			
			sql = " select A.PRODUCT_ITEM_CODE,\r\n"
					+ " A.PRODUCT_ITEM_NAME,\r\n"
					+ " A.PRODUCT_INFO_STND_1,\r\n"
					+ " A.PRODUCT_INFO_STND_2,\r\n"
					+ " B.CHILD_TBL_TYPE PRODUCT_MTRL_CLSFC,\r\n"
					+ " C.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_1_NAME,\r\n"
					+ " D.CHILD_TBL_TYPE PRODUCT_ITEM_CLSFC_2_NAME,\r\n"
					+ " E.CHILD_TBL_TYPE PRODUCT_UNIT_NAME,\r\n"
					+ " F.CHILD_TBL_TYPE PRODUCT_MATERIAL_NAME,\r\n"
					+ " A.PRODUCT_UNIT_PRICE, \r\n"
					+ " ppst.packaging_Item PRODUCT_Packaging_Item, ppst.packaging_Small PRODUCT_Packaging_Small, ppst.packaging_Large PRODUCT_Packaging_Large\r\n"
					+ " from Product_Info_tbl A\r\n"
					+ " inner join DTL_TBL B on A.PRODUCT_MTRL_CLSFC = B.CHILD_TBL_NO\r\n"
					+ " inner join DTL_TBL C on A.Product_Item_CLSFC_1 = C.CHILD_TBL_NO\r\n"
					+ " inner join DTL_TBL D on A.Product_Item_CLSFC_2 = D.CHILD_TBL_NO\r\n"
					+ " inner join DTL_TBL E on A.Product_Unit = E.CHILD_TBL_NO\r\n"
					+ " inner join DTL_TBL F on A.Product_Material = F.CHILD_TBL_NO\r\n"
					+ " inner join Paldang_Packaging_Standard_tbl ppst ON A.Product_Info_STND_2 = ppst.packaging_No \r\n"
					+ " where (A.PRODUCT_INFO_STND_2 like '%"+item_Word+"%')\r\n"
					+ " and A.PRODUCT_MTRL_CLSFC like '%" + search_value + "%'\r\n"
					+ " and A.PRODUCT_USE_STATUS='1'";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<ItemDto> list = new ArrayList<ItemDto>();

			while (rs.next()) {
				ItemDto data = new ItemDto();

				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_INFO_STND_2(rs.getString("PRODUCT_INFO_STND_2"));
				data.setPRODUCT_MTRL_CLSFC(rs.getString("PRODUCT_MTRL_CLSFC"));
				data.setPRODUCT_UNIT_NAME(rs.getString("PRODUCT_UNIT_NAME"));
				data.setPRODUCT_MATERIAL_NAME(rs.getString("PRODUCT_MATERIAL_NAME"));
				data.setPRODUCT_ITEM_CLSFC_1_NAME(rs.getString("PRODUCT_ITEM_CLSFC_1_NAME"));
				data.setPRODUCT_ITEM_CLSFC_2_NAME(rs.getString("PRODUCT_ITEM_CLSFC_2_NAME"));
				
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				data.setPRODUCT_Packaging_Item(rs.getString("PRODUCT_Packaging_Item"));
				data.setPRODUCT_Packaging_Small(rs.getDouble("PRODUCT_Packaging_Small"));
				data.setPRODUCT_Packaging_Large(rs.getDouble("PRODUCT_Packaging_Large"));
				list.add(data);
			}	
		
			rs.close();
			pstmt.close();
			conn.close();

			return list;
		}
	
	//itemPopup
	@GetMapping("/tablet/itemPopupSelect")
	public List<ItemDto> tabletitemPopupSelect(
				@RequestParam(value = "item_Word", required = false) String item_Word,
				@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			conn = dataSource.getConnection();
			
			String sql = "";
			
			sql = " select PRODUCT_ITEM_CODE,\r\n"
					+ " PRODUCT_ITEM_NAME,\r\n"
					+ " PRODUCT_INFO_STND_1,\r\n"
					+ " PRODUCT_UNIT_PRICE\r\n"
					+ " from Product_Info_tbl\r\n"
					+ " where (PRODUCT_ITEM_CODE like '%" + item_Word + "%' or PRODUCT_ITEM_NAME like '%" + item_Word + "%')\r\n"
					+ " and PRODUCT_USE_STATUS='true'"
					+ " and PRODUCT_MTRL_CLSFC in ("+search_value+")";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<ItemDto> list = new ArrayList<ItemDto>();

			while (rs.next()) {
				ItemDto data = new ItemDto();

				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				list.add(data);
			}	
		
			rs.close();
			pstmt.close();
			conn.close();

			return list;
	}
	
	//itemPopup - ???????????? ?????????
	@GetMapping("/tablet/itemPopupSelect2")
	public List<ItemDto> tabletitemPopupSelect2(
				@RequestParam(value = "item_Word", required = false) String item_Word,
				@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
			
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			conn = dataSource.getConnection();
			
			String terms = null;
			String terms_sql = "select CHILD_TBL_RMARK from DTL_TBL\r\n"
					+ "where NEW_TBL_CODE = 37 and CHILD_TBL_TYPE = '"+search_value+"'";
			
			pstmt = conn.prepareStatement(terms_sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				terms = rs.getString("CHILD_TBL_RMARK");
			}
			
			String sql = "";
			
			sql = " select PRODUCT_ITEM_CODE,\r\n"
					+ " PRODUCT_ITEM_NAME,\r\n"
					+ " PRODUCT_INFO_STND_1,\r\n"
					+ " PRODUCT_UNIT_PRICE\r\n"
					+ " from Product_Info_tbl\r\n"
					+ " where PRODUCT_INFO_STND_1 like '%" + item_Word.trim() + "%'\r\n"
					+ " and PRODUCT_USE_STATUS='true'"
					+ " and PRODUCT_MTRL_CLSFC in ("+terms+")";
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			List<ItemDto> list = new ArrayList<ItemDto>();

			while (rs.next()) {
				ItemDto data = new ItemDto();

				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				list.add(data);
			}	
		
			rs.close();
			pstmt.close();
			conn.close();

			return list;
	}
	
	//itemPopup - ???????????? ?????????
		@GetMapping("/itemPopupSelect2")
		public List<ItemDto> itemPopupSelect2(
					@RequestParam(value = "item_Word", required = false) String item_Word,
					@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rs = null;
				
				conn = dataSource.getConnection();
				
				String terms = null;
				String terms_sql = "select CHILD_TBL_RMARK from DTL_TBL\r\n"
						+ "where NEW_TBL_CODE = 37 and CHILD_TBL_TYPE = '"+search_value+"'";
				
				pstmt = conn.prepareStatement(terms_sql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					terms = rs.getString("CHILD_TBL_RMARK");
				}
				System.out.println("============");
				System.out.println(terms);
				
				String sql = "";
				
				sql = " select PRODUCT_ITEM_CODE,\r\n"
						+ " PRODUCT_ITEM_NAME,\r\n"
						+ " PRODUCT_INFO_STND_1,\r\n"
						+ " PRODUCT_UNIT_PRICE\r\n"
						+ " from Product_Info_tbl\r\n"
						+ " where PRODUCT_INFO_STND_1 like '%" + item_Word + "%'\r\n"
						+ " and PRODUCT_USE_STATUS='true'"
						+ " and PRODUCT_MTRL_CLSFC in ("+terms+")";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				List<ItemDto> list = new ArrayList<ItemDto>();

				while (rs.next()) {
					ItemDto data = new ItemDto();

					data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
					data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
					data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
					data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
					list.add(data);
				}	
			
				rs.close();
				pstmt.close();
				conn.close();

				return list;
		}

	// machinePopup
	@GetMapping("/machinePopupSelect")
	public List<MachineDto> machinePopupSelect(
			@RequestParam(value = "machine_Word", required = false) String machine_Word,
			@RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
		
		String sql = "";
		
		sql = " select Equipment_Info_Code, Equipment_Info_Name from Equipment_Info_tbl eit\r\n"
			+ " inner join DTL_TBL dt on eit.Equipment_Type = dt.Child_TBL_No\r\n"
			+ " where (Equipment_Info_Code like '%" + machine_Word + "%' or Equipment_Info_Name like '%" + machine_Word + "%') and dt.Child_TBL_No='"+ search_value +"'";

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<MachineDto> list = new ArrayList<MachineDto>();
		
		while (rs.next()) {
			MachineDto data = new MachineDto();

			data.setEQUIPMENT_INFO_CODE(rs.getString("Equipment_Info_Code"));
			data.setEQUIPMENT_INFO_NAME(rs.getString("Equipment_Info_Name"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//defectPopup
	@RequestMapping(value = "/defectPopupSelect", method = { RequestMethod.GET })
	public List<DefectInfoDto> defectPopupSelect(
			@RequestParam(value = "defect_Word", required = false) String defect_Word) throws SQLException {

		String sql = "";
		
		sql = " select DEFECT_CODE, DEFECT_NAME from DEFECT_INFO_TBL\r\n"
			+ " where (DEFECT_CODE like '%" + defect_Word + "%' or DEFECT_NAME like '%" + defect_Word + "%')\r\n"
			+ " and DEFECT_USE_STATUS='true'";
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<DefectInfoDto> list = new ArrayList<DefectInfoDto>();

		while (rs.next()) {
			DefectInfoDto data = new DefectInfoDto();
			data.setDefect_Code(rs.getString("DEFECT_CODE"));
			data.setDefect_Name(rs.getString("DEFECT_NAME"));
			list.add(data);
		}

		return list;
	}
	
	// customerPopup
    @RequestMapping(value = "/customerPopupSelect", method = RequestMethod.GET)
    public List<CustomerDto> customerPopupSelect(
          @RequestParam(value = "cus_Word", required = false) String cus_Word,
          @RequestParam(value = "search_value", required = false) String search_value) throws SQLException {
       
       String sql = "";

       sql = "select A.Cus_Code, A.Cus_Name\r\n"
       		+ "from Customer_tbl A\r\n"
       		+ "inner join ("
       			+ "select * from DTL_TBL where NEW_TBL_CODE = '28'"
       		+ ") B on A.Cus_Clsfc = B.CHILD_TBL_NO\r\n"
       		+ "where (A.Cus_Code like '%"+cus_Word+"%' or A.Cus_Name like '%"+cus_Word+"%')";
       
       if(!search_value.equals("all")) {
           sql += " and B.CHILD_TBL_RMARK='"+search_value+"'";
       }

       Connection conn = dataSource.getConnection();
       PreparedStatement pstmt = conn.prepareStatement(sql);
       ResultSet rs = pstmt.executeQuery();

       List<CustomerDto> list = new ArrayList<CustomerDto>();

       while (rs.next()) {
    	   CustomerDto data = new CustomerDto();

          data.setCus_Code(rs.getString("cus_Code"));
          data.setCus_Name(rs.getString("cus_Name"));
          list.add(data);
       }

       rs.close();
       pstmt.close();
       conn.close();

       return list;
    }
    
	//product_check
	@RequestMapping(value = "/product_check", method = RequestMethod.GET)
	public List<ItemDto> product_check(
			@RequestParam(value = "PRODUCT_ITEM_CODE", required = false) String PRODUCT_ITEM_CODE) throws SQLException {
		
		String sql = "";
		
		//??? select ????????? ????????? ????????? select??? ?????????
		sql = "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
				+ "from Product_Info_tbl \r\n"
				+ " where (PRODUCT_ITEM_NAME = '" + PRODUCT_ITEM_CODE + "' or PRODUCT_ITEM_CODE = '" + PRODUCT_ITEM_CODE + "')\r\n"
				+ "union\r\n"
				+ "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
				+ "from Product_Info_tbl \r\n"
				+ " where (PRODUCT_ITEM_NAME like '%" + PRODUCT_ITEM_CODE + "%' or PRODUCT_ITEM_CODE like '%" + PRODUCT_ITEM_CODE + "%')\r\n"
				+ "and not exists (\r\n"
				+ "	select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
					+ " from Product_Info_tbl \r\n"
					+ " where (PRODUCT_ITEM_NAME = '" + PRODUCT_ITEM_CODE + "' or PRODUCT_ITEM_CODE = '" + PRODUCT_ITEM_CODE + "'));";		

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();

		List<ItemDto> list = new ArrayList<ItemDto>();
		
		while (rs.next()) {
			ItemDto data = new ItemDto();

			data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
			data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
			data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	//product_check
		@RequestMapping(value = "/product_stnd2_check", method = RequestMethod.GET)
		public List<ItemDto> product_stnd2_check(
				@RequestParam(value = "Product_Stnd2", required = false) String Product_Stnd2) throws SQLException {
			
			String sql = "";
			
			//??? select ????????? ????????? ????????? select??? ?????????
			sql = "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
					+ "from Product_Info_tbl \r\n"
					+ " where (PRODUCT_INFO_STND_2 = '" + Product_Stnd2 + "')\r\n"
					+ "union\r\n"
					+ "select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
					+ "from Product_Info_tbl \r\n"
					+ " where (PRODUCT_INFO_STND_2 like '%" + Product_Stnd2 + "%')\r\n"
					+ "and not exists (\r\n"
					+ "	select PRODUCT_ITEM_CODE, PRODUCT_ITEM_NAME, PRODUCT_INFO_STND_1, PRODUCT_UNIT_PRICE \r\n"
						+ " from Product_Info_tbl \r\n"
						+ " where (PRODUCT_INFO_STND_2 = '" + Product_Stnd2 + "'));";		

			Connection conn = dataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();

			List<ItemDto> list = new ArrayList<ItemDto>();
			
			while (rs.next()) {
				ItemDto data = new ItemDto();

				data.setPRODUCT_ITEM_CODE(rs.getString("PRODUCT_ITEM_CODE"));
				data.setPRODUCT_ITEM_NAME(rs.getString("PRODUCT_ITEM_NAME"));
				data.setPRODUCT_INFO_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
				data.setPRODUCT_UNIT_PRICE(rs.getInt("PRODUCT_UNIT_PRICE"));
				list.add(data);
			}
			rs.close();
			pstmt.close();
			conn.close();

			return list;
		}
	
	//customer_check
	@RequestMapping(value = "/customer_check", method = RequestMethod.GET)
	public List<CustomerDto> customer_check(
			@RequestParam(value = "Cus_Code", required = false) String cus_Code) throws SQLException {
		
		String sql = "";

		//??? select ????????? ????????? ????????? select??? ?????????
		sql = "select Cus_Code, Cus_Name \r\n"
				+ "from Customer_tbl\r\n"
				+ " where (Cus_Name = '" + cus_Code + "' or Cus_Code = '" + cus_Code + "')\r\n"
				+ "union\r\n"
				+ "select Cus_Code, Cus_Name \r\n"
				+ "from Customer_tbl\r\n"
				+ " where (Cus_Name like '%" + cus_Code + "%' or Cus_Code like '%" + cus_Code + "%')\r\n"
				+ "and not exists (\r\n"
				+ "	select Cus_Code, Cus_Name \r\n"
					+ "from Customer_tbl\r\n"
					+ " where (Cus_Name = '" + cus_Code + "' or Cus_Code = '" + cus_Code + "'))";		

		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<CustomerDto> list = new ArrayList<CustomerDto>();
		
		while (rs.next()) {
			CustomerDto data = new CustomerDto();

			data.setCus_Code(rs.getString("cus_Code"));
			data.setCus_Name(rs.getString("cus_Name"));
			list.add(data);
		}

		rs.close();
		pstmt.close();
		conn.close();

		return list;
	}
	
	@RequestMapping(value = "/equipment_check", method = RequestMethod.GET)
	public List<MachineDto> equipment_check(
			@RequestParam(value = "EQUIPMENT_INFO_CODE", required = false) String EQUIPMENT_INFO_CODE){
		
		//??? select ????????? ????????? ????????? select??? ?????????
		String sql = "SELECT Equipment_Info_Code, Equipment_Info_Name \r\n"
				+ "FROM Equipment_Info_tbl \r\n"
				+ "where (Equipment_Info_Name = ? or Equipment_Info_Code = ?)\r\n"
				+ "union\r\n"
				+ "SELECT Equipment_Info_Code, Equipment_Info_Name \r\n"
				+ "FROM Equipment_Info_tbl \r\n"
				+ "where (Equipment_Info_Name like ? or Equipment_Info_Code like ?)\r\n"
				+ "and not exists(\r\n"
				+ "SELECT Equipment_Info_Code, Equipment_Info_Name \r\n"
				+ "FROM Equipment_Info_tbl \r\n"
				+ "where (Equipment_Info_Name = ? or Equipment_Info_Code = ?));";
		
		System.out.println(sql);
		return jdbctemplate.query(sql, new RowMapper<MachineDto>(){
			@Override
			public MachineDto mapRow(ResultSet rs, int rowNum) throws SQLException {
				MachineDto data = new MachineDto();
				data.setEQUIPMENT_INFO_CODE(rs.getString("EQUIPMENT_INFO_CODE"));
				data.setEQUIPMENT_INFO_NAME(rs.getString("EQUIPMENT_INFO_NAME"));
				return data;
			}
		}, EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_CODE, "%"+EQUIPMENT_INFO_CODE+"%", 
				"%"+EQUIPMENT_INFO_CODE+"%", EQUIPMENT_INFO_CODE, EQUIPMENT_INFO_CODE);
	}
	
	@GetMapping("/hometaxApiPopupSelect")
	public List<HometaxApiDto> hometaxApiPopupSelect(){
		return hometaxApiService.hometaxApiDataSearch();
	}
	
	@GetMapping("/paldangPackagingListSelect")
	public List<PaldangPackagingStandardDto> paldangPackagingListDao() {
		return paldangPackagingService.paldangPackagingListDao();
	}
}