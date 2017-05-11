package com.nandeep.ofooty.util;

public abstract class LookUpMatchRequestUtil {
	
	@SuppressWarnings("unused")
	private MatchRequestUtil matchRequestUtil;
	
	public abstract MatchRequestUtil createMRUtil();

	public void setMatchRequestUtil(MatchRequestUtil matchRequestUtil) {
		this.matchRequestUtil = matchRequestUtil;
	}

	
	
}
