package com.wundermancommerce.interviewtests.graph;

import java.util.Objects;


public class Person {
	
	private String name;
	private String email;
	private int age;

	
	Person(String name, String email, int age) {
	    this.name = name;
	    this.email = Objects.requireNonNull(email, "email must not be null");
	    this.age = age;
	  }
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
	
	@Override
	  public boolean equals(Object other) {
	    if(other instanceof Person) {
	      Person that = (Person) other;
	      return this.name.equals(that.name) && this.email.equals(that.name)
	          && this.age == that.age;
	    }
	    return false;
	  }

	  @Override
	  public int hashCode() {
	    return Objects.hash(name, email, age);
	  }

	  @Override
	  public String toString() {
	    return "(" + name + ", " + email + ", "+ age + ")";
	  }
}
