package ch.hsr.nsg.themenrundgang.vm;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;


public class SubjectViewModel {


    private final SubjectRepository subjectRepository;

    public ArrayList<UiSubject> getSubjects() {
        return subjects;
    }

    private final ArrayList<UiSubject> subjects;

    public SubjectViewModel(NsgApi nsgApi, SubjectRepository subjectRepository) {

        this.subjectRepository = subjectRepository;
        this.subjects = new ArrayList<UiSubject>();


        for(Subject s : subjectRepository.allToplevelSubjects()) {
            int imageId = subjectRepository.imageForSubject(s);
            String imageUrl = imageId == -1 ? null : nsgApi.getImagePath(imageId);
            subjects.add(UiSubject.newInstance(s, imageUrl));
        }
    }


    public static class UiSubject extends Subject {

        public static UiSubject newInstance(Subject subject, String imageUrl) {
            UiSubject uiSubject = new UiSubject();
            uiSubject.setId(subject.getId());
            uiSubject.setDescription(subject.getDescription());
            uiSubject.setName(subject.getName());
            uiSubject.setParentId(subject.getParentId());
            uiSubject.setImageUrl(imageUrl);
            return uiSubject;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean isChecked) {
            this.isChecked = isChecked;
        }


        private boolean isChecked = false;

        private String imageUrl;

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }
    }

}
