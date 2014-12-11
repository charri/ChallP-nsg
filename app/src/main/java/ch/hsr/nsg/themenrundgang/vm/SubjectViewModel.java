package ch.hsr.nsg.themenrundgang.vm;

import java.util.ArrayList;

import ch.hsr.nsg.themenrundgang.applicationService.NsgApi;
import ch.hsr.nsg.themenrundgang.model.Subject;
import ch.hsr.nsg.themenrundgang.model.SubjectRepository;
import ch.hsr.nsg.themenrundgang.utils.OnObservable;


public class SubjectViewModel extends AbstractViewModel {

    public final static String KEY_SUBJECTS = "subjects";

    private final SubjectRepository subjectRepository;

    public ArrayList<UiSubject> getSubjects() {
        return subjects;
    }

    public Subject[] getSubjectsChecked() {
        ArrayList<Subject> rValue = new ArrayList<>();
        for(UiSubject s : subjects) {
            if(s.isChecked())  rValue.add(s);
        }
        return rValue.toArray(new Subject[0]);
    }

    public int getSubjectsCheckedCount() {
        int selected = 0;
        for(UiSubject s : subjects) {
            if(s.isChecked()) ++selected;
        }
        return selected;
    }

    private final ArrayList<UiSubject> subjects;

    public SubjectViewModel(NsgApi nsgApi, SubjectRepository subjectRepository) {

        this.subjectRepository = subjectRepository;
        this.subjects = new ArrayList<UiSubject>();


        for(Subject s : subjectRepository.allToplevelSubjects()) {
            int imageId = subjectRepository.imageForSubject(s);
            String imageUrl = imageId == -1 ? null : nsgApi.getImagePath(imageId);

            UiSubject uiSubject = UiSubject.newInstance(s, imageUrl);
            uiSubject.setObservable(this);
            subjects.add(uiSubject);
        }

        notifyObservers(SubjectViewModel.KEY_SUBJECTS);
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

        private OnObservable observable;

        public void setObservable(OnObservable observable) {
            this.observable = observable;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean isChecked) {
            if(this.isChecked == isChecked) return;

            this.isChecked = isChecked;
            if(observable != null) {
                observable.notifyOn(SubjectViewModel.KEY_SUBJECTS);
            }
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
