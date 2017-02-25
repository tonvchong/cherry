package com.tonvchong.core.util.identity;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tonvchong.core.util.context.ServiceLocator;

/**
 * 
 * 
 * Copyright © 2016全球蜂.
 * 
 * @Title: IDGenerator.java
 * @Project: RBM
 * @date: 2016年3月7日 下午10:30:29
 * @author: tonvchong
 * @Description: 业务流水生成器,规则：PREFIX + yyyyMMdd + 6位随机数
 */
public class SerialNumberGenerator {
	private Object locked = new Object();

	private SequenceDao sequenceDao;

	private static SerialNumberGenerator instance;

	public static SerialNumberGenerator getInstance() {
		if (instance == null) {
			synchronized (SerialNumberGenerator.class) {
				if (instance == null) {
					instance = new SerialNumberGenerator();
				}
			}
		}
		return instance;
	}

	public String generate(String seqname, String timeFormat) {
		if (sequenceDao == null) {
			sequenceDao = (SequenceDao) ServiceLocator.getBean("sequenceDao");
		}

		Sequence seq = null;
		synchronized (locked) {
			seq = sequenceDao.getSequence(seqname);
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String time = sdf.format(new Date());
		String seqstr = fillZero(seq.getCurrentVal(), seq.getMaxVal());
		return seq.getPrefix() + time + seqstr;
	}

	private String fillZero(int seq, int maxval) {
		String seqstr = seq + "";
		int maxlength = String.valueOf(maxval).length();
		if (seqstr.length() < maxlength) {
			int len = maxlength - seqstr.length();
			for (int i = 0; i < len; i++) {
				seqstr = "0" + seqstr;
			}
		}
		return seqstr;
	}

	public static String next(String seqname) {
		return getInstance().generate(seqname,"yyyyMMdd");
	}
	
	public static String nextPayNo(String seqname) {
		return getInstance().generate(seqname,"yyyyMMddHHmmss");
	}

	public static void main(String args[]) {
		System.out
				.println(SerialNumberGenerator.getInstance().fillZero(123, 6));
		System.out.println(SerialNumberGenerator.getInstance()
				.fillZero(2891, 6));
		System.out.println(SerialNumberGenerator.getInstance().fillZero(92891,
				6));
		System.out.println(SerialNumberGenerator.getInstance().fillZero(100212,
				6));
		System.out.println(SerialNumberGenerator.getInstance().fillZero(
				1002123, 6));
	}
}
