package com.tonvchong.core.util.identity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * 
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: SequenceDao.java
 * @Project: RBM
 * @date: 2016年3月8日 下午10:26:26
 * @author: tonvchong
 * @Description: 操作t_sys_sequence
 */
public class SequenceDao extends JdbcDaoSupport {
	public Sequence getSequence(String seqname) {
		String sql = "select * from t_sys_sequence where seqname=?";
		Sequence seq = (Sequence) getJdbcTemplate().queryForObject(sql, new Object[] { seqname },
				new SequenceRowMapper());		
		int newSeqVal = seq.getCurrentVal() + seq.getIncrementVal();
		if (newSeqVal > seq.getMaxVal()) {
			newSeqVal = 1;
		}	
		String updateSql = "update t_sys_sequence set currentval=? where seqname=?";
		getJdbcTemplate().update(updateSql,newSeqVal,seqname);

		return seq;
	}

	private static class SequenceRowMapper implements RowMapper<Sequence> {

		public Sequence mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sequence seq = new Sequence();
			seq.setSeqname(rs.getString("seqname"));
			seq.setMaxVal(rs.getInt("maxval"));
			seq.setIncrementVal(rs.getInt("incrementval"));
			seq.setCurrentVal(rs.getInt("currentval"));
			seq.setPrefix(rs.getString("prefix"));
			return seq;
		}

	}
}
