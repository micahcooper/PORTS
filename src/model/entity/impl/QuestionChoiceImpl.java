package model.entity.impl;

import model.entity.*;
import java.util.List;

public class QuestionChoiceImpl implements QuestionChoice{

	private int id;
	private String text;
	private int number;

	public QuestionChoiceImpl(String text){
		this.id = 0;
		this.text = text;
	}

	public QuestionChoiceImpl(int id, String text){
		this.id = id;
		this.text = text;
	}
	
	public QuestionChoiceImpl(int id, String text, int number){
		this.id = id;
		this.text = text;
		this.number = number;
	}
	
	public int getId(){return id;}
	public String getText(){return text;}
	public int getNumber(){ return number; }
	
	public void setId(int id){this.id = id;}
	public void setText(String text){this.text = text;}
	public void setNumber(int number){ this.number = number; }

}
