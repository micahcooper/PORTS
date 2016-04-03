package model.entity;

import except.*;
import java.util.List;
import java.util.Iterator;

public interface ExamQuestion {
	
	public int getId();
	public Iterator getChoices() throws PortsException;
	public String getText();
	public int getAnswer();
	
	public void setId(int id);
	public void addChoice(QuestionChoice choice, int number) throws PortsException;
	public void removeChoice(QuestionChoice choice) throws PortsException;
	public void setText(String text);
	public void setAnswer(int answer);

}
