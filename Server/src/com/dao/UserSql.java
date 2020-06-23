package com.dao;

public interface UserSql {
	// SELECT 구문
	String select_checkIP = "SELECT COUNT(*) FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String select_nickname = "SELECT NICKNAME FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String select_score = "SELECT * FROM MPJ_CURD.GAME_USER WHERE NICKNAME = ?";
	
	// INSERT 구문
	String insertAll = "INSERT INTO MPJ_CURD.GAME_USER VALUES(MPJ_CURD.USER_SEQ.NEXTVAL, ?, ?, SYSDATE)";
	
	// DELETE 구문
	
	// UPDATE 구문
	String update = "UPDATE MPJ_CURD.GAME_USER SET SCORE = ? WHERE NICKNAME = ?";
}