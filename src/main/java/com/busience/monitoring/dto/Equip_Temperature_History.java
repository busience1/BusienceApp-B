package com.busience.monitoring.dto;

public class Equip_Temperature_History {
	String Temp_Value,Temp_Time,Temp_EquipCode,StartTime,EndTime,Temp_No;

	public String getTemp_No() {
		return Temp_No;
	}

	public void setTemp_No(String temp_No) {
		Temp_No = temp_No;
	}

	public String getStartTime() {
		return StartTime;
	}

	public void setStartTime(String startTime) {
		StartTime = startTime;
	}

	public String getEndTime() {
		return EndTime;
	}

	public void setEndTime(String endTime) {
		EndTime = endTime;
	}

	public String getTemp_Value() {
		return Temp_Value;
	}

	public void setTemp_Value(String temp_Value) {
		Temp_Value = temp_Value;
	}

	public String getTemp_Time() {
		return Temp_Time;
	}

	public void setTemp_Time(String temp_Time) {
		Temp_Time = temp_Time;
	}

	public String getTemp_EquipCode() {
		return Temp_EquipCode;
	}

	public void setTemp_EquipCode(String temp_EquipCode) {
		Temp_EquipCode = temp_EquipCode;
	}

	@Override
	public String toString() {
		return "Equip_Temperature_History [Temp_Value=" + Temp_Value + ", Temp_Time=" + Temp_Time + ", Temp_EquipCode="
				+ Temp_EquipCode + ", StartTime=" + StartTime + ", EndTime=" + EndTime + ", Temp_No=" + Temp_No + "]";
	}

	
}
