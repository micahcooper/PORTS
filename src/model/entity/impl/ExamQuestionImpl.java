package model.entity.impl;

import persist.*;
import except.*;
import model.entity.*;
import java.util.List;
import java.util.Iterator;

public class ExamQuestionImpl implements ExamQuestion{

	private int id;
	private String text;
	private int answer;

	public ExamQuestionImpl(int id, String text, int answer){
		this.id = id;
		this.text = text;
		this.answer = answer;
	}
	
	public ExamQuestionImpl(String text, int answer){
		this.id = 0;
		this.text = text;
		this.answer = answer;
	}
	
	public int getId(){return id;}
	public Iterator getChoices() throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		return pm.restoreQuestionChoices(id);
	}
	public String getText(){return text;}
	public int getAnswer(){return answer;}
	
	public void setId(int id){this.id = id;}
	public void addChoice(QuestionChoice choice, int number) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.addChoiceToQuestion(choice.getId(), id, number);
	}
	public void removeChoice(QuestionChoice choice) throws PortsException{
		PersistenceModule pm = PersistenceModuleFactory.createPersistenceModule();
		pm.removeChoiceFromQuestion(choice.getId(), id);
	}
	public void setText(String text){this.text = text;}
	public void setAnswer(int answer){this.answer = answer;}

}
