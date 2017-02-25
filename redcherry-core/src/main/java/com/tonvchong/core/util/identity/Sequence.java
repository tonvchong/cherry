package com.tonvchong.core.util.identity;

import lombok.Data;

@Data
public class Sequence implements java.io.Serializable {
	private String seqname;
	private String prefix;
	private int currentVal;
	private int maxVal;
	private int incrementVal;	
}
