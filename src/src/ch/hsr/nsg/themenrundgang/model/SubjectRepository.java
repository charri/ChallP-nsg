package ch.hsr.nsg.themenrundgang.model;

public interface SubjectRepository {
	void insertOrUpdate(Subject subject);
	
	Subject[] allToplevelSubjects();
	
	Subject[] allSubjectsByParent(Subject parent);
}
