package model.entity;

import except.*;
import java.util.Date;

public interface Exam {
	
	public Date getDate();
	public int getId();
	public String getName();
	public Section getSection() throws PortsException;
	public int getSectionId();
	public int getLength();
	public String getDirections();
	public String getGeneralInstructions();
	public String getSpecialInstructions();

	public void setDate(Date edate);
	public void setId(int id);	
	public void setName(String name);
	public void setSection(int section);
	public void setLength(int length);
	public void setDirections(String directions);	
	public void setGeneralInstructions(String general_instructions);
	public void setSpecialInstructions(String special_instructions);

}
