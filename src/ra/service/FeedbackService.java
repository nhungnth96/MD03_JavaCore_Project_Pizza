package ra.service;

import ra.database.DataBase;
import ra.manager.Main;
import ra.model.feedback.Feedback;

import java.util.ArrayList;
import java.util.List;

public class FeedbackService implements IGenericService<Feedback,Integer> {
    private List<Feedback> feedbacks;
    public FeedbackService() {
        List<Feedback> feedbackList = (List<Feedback>) DataBase.readFromFile(DataBase.FEEDBACK_PATH);
        if (feedbackList == null) {
            feedbackList = new ArrayList<>();
        }
        this.feedbacks = feedbackList;
    }
    public List<Feedback> getAll() {
        return feedbacks;
    }



    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void save(Feedback feedback) {

        if (findByIdForAdmin(feedback.getFeedbackId()) == null) {
            // add
            feedbacks.add(feedback);
        } else {
            // update
            feedbacks.set(feedbacks.indexOf(findByIdForAdmin(feedback.getFeedbackId())), feedback);
        }
//         save to DB
        DataBase.writeToFile(feedbacks, DataBase.FEEDBACK_PATH);


    }

    public int getNewId() {
        int maxId = 0;
        for (Feedback feedback : feedbacks) {
            if (feedback.getFeedbackId() > maxId) {
                maxId = feedback.getFeedbackId();
            }
        }
        return maxId + 1;
    }



    public Feedback findByIdForAdmin(int id) {
        for (Feedback feedback : feedbacks) {
            if (feedback.getFeedbackId() == id) {
                return feedback;
            }
        }
        return null;
    }

    @Override
    public Feedback findById(Integer id) {
        for (Feedback feedback : findFeedbackByUserId()) {
            if (feedback.getFeedbackId() == id) {
                return feedback;
            }
        }
        return null;
    }

    public List<Feedback> findFeedbackByUserId() {
        List<Feedback> findList = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            if (feedback.getUserId() == Main.currentLogin.getId()) {
                findList.add(feedback);
            }
        }
        return findList;
    }
}
