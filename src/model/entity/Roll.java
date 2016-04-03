package model.entity;

import except.*;
import java.util.Iterator;

public interface Roll{

	public Section getSection() throws PortsException;
	public int getSectionId();
	public Iterator getStudents() throws PortsException;

	public void setSection( int section_id );
	public void addStudent( Student s ) throws PortsException;
	public void removeStudent( Student s ) throws PortsException;
}
