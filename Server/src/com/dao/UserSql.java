package com.dao;

public interface UserSql {
	// SELECT 구문
	String select_checkIP = "SELECT COUNT(*) FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String select_nickname = "SELECT NICKNAME FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	
	// INSERT 구문
	String insertAll = "INSERT INTO MPJ_CURD.GAME_USER VALUES(MPJ_CURD.NUM_SEQ.NEXTVAL, ?, ?, SYSDATE)";
	
	// DELETE 구문
	
	// UPDATE 구문

	// 은혜 언니가 만든 SQL 구문
	String selectUser = "SELECT NO, IP, NICKNAME, SCORE, REGDATE FROM MPJ_CURD.GAME_USER WHERE IP = ? and nickname= ?";
	String ch_ip = "SELECT count(IP) FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String insert_allinfo = "INSERT INTO MPJ_CURD.GAME_USER(NO, IP, NICKNAME) values(MPJ_CURD.NUM_SEQ.NEXTVAL, ?, ?)";
	String ch_nick = "SELECT NICKNAME FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String ch_nickip = "SELECT NICKNAME FROM MPJ_CURD.GAME_USER WHERE IP = ?";
}