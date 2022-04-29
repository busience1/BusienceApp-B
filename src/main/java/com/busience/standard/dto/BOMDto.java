package com.busience.standard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BOMDto {
	private Integer BOM_ID;
	private String BOM_Parent_ItemCode;
	private String BOM_ItemCode;
	private String BOM_ItemName;
	private String BOM_STND_1;
	private double BOM_Qty;
	private String BOM_Unit_Name;
	private String BOM_State;
	private String BOM_ChildExist; 
	private String BOM_Modifier; 
	private String BOM_Modify_Date;
}
