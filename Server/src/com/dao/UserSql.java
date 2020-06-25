package com.dao;

public interface UserSql {
	
	String sign_up_gu = "BEGIN SIGN_UP_GU(?, ?); END;";
	String log_in_gu = "{ ? = call LOG_IN_GU(?, ?) }";
	
	String ch_ip = "SELECT count(IP) FROM GAME_USER WHERE IP = ?";
	String selectUser = "SELECT no, ip, nickname, score, regdate FROM MPJ_CURD.GAME_USER WHERE IP = ? and nickname= ?";
	String ch_nick = "select nickname from MPJ_CURD.GAME_USER where ip = ?";
	String insert_allinfo = "Insert into MPJ_CURD.GAME_USER(no, ip, nickname) values(MPJ_CURD.num_seq.nextval,?, ?)";
	String ch_nickip = "select nickname from MPJ_CURD.GAME_USER where ip=?";
	
	// SELECT 구문
	String select_checkIP = "SELECT COUNT(*) FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	String select_nickname = "SELECT NICKNAME FROM MPJ_CURD.GAME_USER WHERE IP = ?";
		
	// INSERT 구문
	String insertAll = "INSERT INTO MPJ_CURD.GAME_USER(NO, IP, NICKNAME) VALUES(MPJ_CURD.GAMEUSER_SEQ.NEXTVAL, ?, ?)";
}