package com.example.ormtutorial;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;



@DatabaseTable
public class Person {
	public static final String FIELD_NAME = "name";
	
	@DatabaseField(columnName= FIELD_NAME )
	private String name;
	
	public Person(){
		
	}
	public Person(String name){
		this.name = name;
	}
	public String getName(){
		return this.name;
	}
}
