package com.busience.standard.controller;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busience.common.dto.SearchDto;
import com.busience.standard.dto.BOMDto;
import com.busience.standard.dto.ItemDto;
import com.busience.standard.service.BOMService;

@RestController("BOMRestController")
@RequestMapping("BOMRest")
public class BOMRestController {
	
	@Autowired
	BOMService bomService;
	
	@Autowired
	DataSource dataSource;
	
	@GetMapping("/BOMitemList2")
	public List<ItemDto> BOMitemList2(SearchDto searchDto){
		return bomService.BOMitemList(searchDto);
	}
	
	//BOMBOMList
	@RequestMapping(value = "/BOMBOMList",method = RequestMethod.GET)
	public List<BOMDto> BOMBOMList(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = " SELECT\r\n"
				+ " A.BOM_ID,\r\n"
				+ " A.BOM_Parent_ItemCode,\r\n"
				+ " A.BOM_ItemCode,\r\n"
				+ " B.PRODUCT_ITEM_NAME,\r\n"
				+ " B.PRODUCT_INFO_STND_1,\r\n"
				+ " B.PRODUCT_INFO_STND_2,\r\n"
				+ " H.packaging_Item PRODUCT_INFO_STND_2_NAME,\r\n"
				+ " E.CHILD_TBL_TYPE BOM_CLSFC_1_NAME,\r\n"
				+ "	F.CHILD_TBL_TYPE BOM_CLSFC_2_NAME,"
				+ " C.CHILD_TBL_TYPE BOM_State,\r\n"
				+ " A.BOM_Qty,\r\n"
				+ " D.CHILD_TBL_TYPE BOM_Unit_Name,\r\n"
				+ " A.BOM_ChildExist,\r\n"
				+ " A.BOM_Modifier,\r\n"
				+ " A.BOM_Modify_Date\r\n"
				+ " FROM BOM_tbl A\r\n"
				+ " inner join Product_Info_tbl B on A.BOM_ItemCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL C on B.PRODUCT_MTRL_CLSFC = C.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL D on B.PRODUCT_UNIT = D.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL E on B.PRODUCT_ITEM_CLSFC_1 = E.CHILD_TBL_NO\r\n"
				+ " inner join DTL_TBL F on B.PRODUCT_ITEM_CLSFC_2 = F.CHILD_TBL_NO\r\n"
				+ " left outer join Paldang_Packaging_Standard_tbl H on B.PRODUCT_INFO_STND_2 = H.packaging_No"
				+ " where A.BOM_Parent_ItemCode = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMitemList =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOMDto> list = new ArrayList<BOMDto>();
		
		while (rs.next()) {
			BOMDto data = new BOMDto();

			data.setBOM_ID(rs.getInt("BOM_ID"));
			data.setBOM_Parent_ItemCode(rs.getString("BOM_Parent_ItemCode"));
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_STND_2(rs.getString("PRODUCT_INFO_STND_2"));
			data.setBOM_STND_2_NAME(rs.getString("PRODUCT_INFO_STND_2_NAME"));
			data.setBOM_CLSFC_1_NAME(rs.getString("BOM_CLSFC_1_NAME"));
			data.setBOM_CLSFC_2_NAME(rs.getString("BOM_CLSFC_2_NAME"));
			data.setBOM_Qty(rs.getFloat("BOM_Qty"));
			data.setBOM_Unit_Name(rs.getString("BOM_Unit_Name"));
			data.setBOM_State(rs.getString("BOM_State"));
			data.setBOM_ChildExist(rs.getString("BOM_ChildExist"));
			data.setBOM_Modifier(rs.getString("BOM_Modifier"));
			data.setBOM_Modify_Date(rs.getString("BOM_Modify_Date"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BOMImpList
	@RequestMapping(value = "/BOMImpList",method = RequestMethod.GET)
	public List<BOMDto> BOMImpList(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = " SELECT\r\n"
				+ " A.BOM_ItemCode BOM_Parent_ItemCode,\r\n"
				+ " A.BOM_ID,\r\n"
				+ " B.PRODUCT_ITEM_CODE BOM_ItemCode,\r\n"
				+ " B.PRODUCT_ITEM_NAME,\r\n"
				+ " B.PRODUCT_INFO_STND_1,\r\n"
				+ " D.CHILD_TBL_TYPE,\r\n"
				+ " A.BOM_Modifier,\r\n"
				+ " A.BOM_Modify_Date\r\n"
				+ " FROM BOM_tbl A\r\n"
				+ " inner join Product_Info_tbl B on A.BOM_Parent_ItemCode = B.PRODUCT_ITEM_CODE\r\n"
				+ " inner join DTL_TBL D on B.PRODUCT_MTRL_CLSFC = D.CHILD_TBL_NO\r\n"
				+ " where A.BOM_ItemCode = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMImpList =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOMDto> list = new ArrayList<BOMDto>();
		
		while (rs.next()) {
			BOMDto data = new BOMDto();
			data.setBOM_Parent_ItemCode(rs.getString("BOM_Parent_ItemCode"));
			data.setBOM_ID(rs.getInt("BOM_ID"));
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_State(rs.getString("CHILD_TBL_TYPE"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BOMItemInform
	@RequestMapping(value = "/BOMItemInform",method = RequestMethod.GET)
	public List<BOMDto> BOMItemInform(
			@RequestParam(value = "BOM_ItemCode", required = false) String BOM_ItemCode) throws SQLException{
		
		String sql = "SELECT A.PRODUCT_ITEM_CODE BOM_ItemCode, \r\n"
				+ "A.PRODUCT_ITEM_NAME, \r\n"
				+ "A.PRODUCT_INFO_STND_1, \r\n"
				+ "B.CHILD_TBL_TYPE BOM_Unit_Name,\r\n"
				+ "C.CHILD_TBL_TYPE BOM_State\r\n"
				+ "from Product_Info_tbl A\r\n"
				+ "inner join DTL_TBL B on A.PRODUCT_UNIT = B.CHILD_TBL_NO\r\n"
				+ "inner join DTL_TBL C on A.PRODUCT_MTRL_CLSFC = C.CHILD_TBL_NO\r\n"
				+ "where A.PRODUCT_ITEM_CODE = '"+BOM_ItemCode+"'";
		
		System.out.println("BOMItemInform =" + sql);
		
		Connection conn = dataSource.getConnection();
		PreparedStatement pstmt = conn.prepareStatement(sql);
		ResultSet rs = pstmt.executeQuery();
		
		List<BOMDto> list = new ArrayList<BOMDto>();
		
		while (rs.next()) {
			BOMDto data = new BOMDto();
			
			data.setBOM_ItemCode(rs.getString("BOM_ItemCode"));
			data.setBOM_ItemName(rs.getString("PRODUCT_ITEM_NAME"));
			data.setBOM_STND_1(rs.getString("PRODUCT_INFO_STND_1"));
			data.setBOM_Unit_Name(rs.getString("BOM_Unit_Name"));
			data.setBOM_State(rs.getString("BOM_State"));

			list.add(data);
		}
		rs.close();
		pstmt.close();
		conn.close();
		
		return list;
	}
	
	//BBL_Save
	@RequestMapping(value = "/BBL_Save", method = RequestMethod.POST)
	public String BBL_Save(HttpServletRequest request, Model model)
			throws ParseException, SQLException, UnknownHostException, ClassNotFoundException, org.json.simple.parser.ParseException {
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		
		HttpSession httpSession = request.getSession();
		String modifier = (String) httpSession.getAttribute("id");
		
		String BBL_Save_sql = null;
		String BBL_update_sql1 = null;
		String BBL_update_sql2 = null;
		
		Connection conn = null;                                    

		PreparedStatement pstmt = null;

		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				//BOM_tbl??? insert or update
				BBL_Save_sql = " insert into BOM_tbl(\r\n"
				+ "BOM_ID,\r\n"
				+ "BOM_Parent_ItemCode,\r\n"
				+ "BOM_ItemCode,\r\n"
				+ "BOM_Qty,\r\n"
				+ "BOM_Modifier,\r\n"
				+ "BOM_Modify_Date\r\n"
				+ ") values(\r\n"
				+ ""+obj.get("bom_ID")+",\r\n"
				+ "'"+obj.get("bom_Parent_ItemCode")+"',\r\n"
				+ "'"+obj.get("bom_ItemCode")+"',\r\n"
				+ ""+obj.get("bom_Qty")+",\r\n"
				+ "'"+modifier+"',\r\n"
				+ "now()\r\n"
				+ ") on duplicate key update\r\n"
				+ "BOM_Qty ="+obj.get("bom_Qty");
				
				System.out.println("BBL_Save_sql = " + BBL_Save_sql);
				pstmt = conn.prepareStatement(BBL_Save_sql);
				pstmt.executeUpdate();
				
				//BOM_tbl??? BOM_ChildExist??? ????????????
				BBL_update_sql1 = " update BOM_tbl set BOM_ChildExist = 'Y'"
							+ " where BOM_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"'";
				
				System.out.println("BBL_update_sql1 =" + BBL_update_sql1);
				conn.prepareStatement(BBL_update_sql1).executeUpdate();
				
				//BOM_tbl??? BOM_ChildExist??? ????????????
				BBL_update_sql2 = "update BOM_tbl set BOM_ChildExist = 'Y'\r\n"
							+ " where BOM_ItemCode = '"+obj.get("bom_ItemCode")+"'\r\n"
							+ "and exists (select * from (select * from BOM_tbl where BOM_Parent_ItemCode = '"+obj.get("bom_ItemCode")+"') A)";
								
				System.out.println("BBL_update_sql2 =" + BBL_update_sql2);
				conn.prepareStatement(BBL_update_sql2).executeUpdate();
			}

			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
	
	//BBL_Delete
	@RequestMapping(value = "/BBL_Delete", method = RequestMethod.POST)
	public String BBL_Delete(HttpServletRequest request, Model model)
			throws ParseException, SQLException, UnknownHostException, ClassNotFoundException, org.json.simple.parser.ParseException {
		String originData = request.getParameter("data");
		
		JSONParser parser = new JSONParser();
		JSONArray arr = (JSONArray) parser.parse(originData);
		System.out.println(arr);

		String BBL_Delete_sql = null;
		String BBL_Update_sql = null;
		
		Connection conn = null;                                    

		PreparedStatement pstmt = null;

		String sql_result = null;
		
		try {
			conn = dataSource.getConnection();
			conn.setAutoCommit(false);
			
			for(int i=0;i<arr.size();i++) {
				JSONObject obj = (JSONObject) arr.get(i);
				System.out.println(obj);
				
				//BOM_tbl??? delete
				BBL_Delete_sql = " delete from BOM_tbl"
						+ " where bom_Parent_ItemCode = '" + obj.get("bom_Parent_ItemCode")+"'"
						+ " and bom_ItemCode = '" + obj.get("bom_ItemCode")+"'";
				
				System.out.println("BBL_Delete_sql =" + BBL_Delete_sql);
				
				pstmt = conn.prepareStatement(BBL_Delete_sql);
				pstmt.executeUpdate();
				
				//BOM_tbl??? BOM_ChildExist??? ????????????
				BBL_Update_sql = " update BOM_tbl set BOM_ChildExist = 'N'\r\n"
						+ " where BOM_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"'\r\n"
						+ " and NOT EXISTS (select * from (select * from BOM_tbl where BOM_Parent_ItemCode = '"+obj.get("bom_Parent_ItemCode")+"') A)";
				
				System.out.println("BBL_Update_sql =" + BBL_Update_sql);
				
				conn.prepareStatement(BBL_Update_sql).executeUpdate();
			}

			conn.commit();
			sql_result = "success";
		} catch(SQLException e) {
			e.printStackTrace();
			if(conn!=null) {
				conn.rollback();
			}
			sql_result = "error";
		} finally {
			if(pstmt!=null) {
				pstmt.close();
			}
			if(conn!=null) {
				conn.close();
			}
		}
		
		return sql_result;
	}
}