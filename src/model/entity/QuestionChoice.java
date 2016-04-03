package model.entity;

import java.util.List;

public interface QuestionChoice {
	
	public int getId();
	public String getText();
	public int getNumber();
	
	public void setId(int id);
	public void setText(String text);
	public void setNumber(int num);
}
