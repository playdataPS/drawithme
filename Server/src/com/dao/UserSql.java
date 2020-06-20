package com.dao;

import static common.JDBCTemplate.Close;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vo.User;

public interface UserSql {
	//ip ���� Ȯ��
//	String ch_ip = "SELECT IP FROM MPJ.GAME_USER WHERE IP = ?";
	String ch_ip = "SELECT count(IP) FROM MPJ_CURD.GAME_USER WHERE IP = ?";
	
	
	String selectUser = "SELECT no, ip, nickname, score, regdate FROM MPJ_CURD.GAME_USER WHERE IP = ? and nickname= ?";
//	String selectUser = "SELECT no, ip, nickname, score, regdate FROM MPJ.GAME_USER WHERE IP = ? and nickname= ?";

	//	String ch_nick = "select nickname from MPJ.GAME_USER where ip = ?";
	String ch_nick = "select nickname from MPJ_CURD.GAME_USER where ip = ?";

	//	String insert_allinfo = "Insert into MPJ.GAME_USER(no, ip, nickname) values(MPJ.num_seq.nextval,?, ?)";
	String insert_allinfo = "Insert into MPJ_CURD.GAME_USER(no, ip, nickname) values(MPJ_CURD.num_seq.nextval,?, ?)";
	
	String ch_nickip = "select nickname from MPJ_CURD.GAME_USER where ip=?";
//	String ch_nickip = "select nickname from MPJ.GAME_USER where ip=?";
	//없어도 될것같은뎅
}