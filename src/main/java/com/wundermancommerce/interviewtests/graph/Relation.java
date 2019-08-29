package com.wundermancommerce.interviewtests.graph;


public class Relation {
	private String emailFirst;
	private String emailSecond;
	private String relationType;
	
	
	
	
	
	public Relation(String emailFirst, String emailSecond, String relationType) {
		super();
		this.emailFirst = emailFirst;
		this.emailSecond = emailSecond;
		this.relationType = relationType;
	}
	public String getEmailFirst() {
		return emailFirst;
	}
	public void setEmailFirst(String emailFirst) {
		this.emailFirst = emailFirst;
	}
	public String getEmailSecond() {
		return emailSecond;
	}
	public void setEmailSecond(String emailSecond) {
		this.emailSecond = emailSecond;
	}
	public String getRelationType() {
		return relationType;
	}
	public void setRelationType(String relationType) {
		this.relationType = relationType;
	}
	
	
	
	
	
	
	
	
	
	

	
	
}
