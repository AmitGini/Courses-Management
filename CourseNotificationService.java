import java.util.ArrayList;

public class CourseNotificationService implements NotificationService {

    private ArrayList<Student> subscribers;

    public CourseNotificationService(){
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(User subscriber){
        this.subscribers.add((Student)subscriber);
    }

    @Override
    public void removeSubscriber(User subscriber){
        this.subscribers.remove((Student) subscriber);
    }

    @Override
    public void updateAll(String courseName){
        for(Student subscriber : this.subscribers){
            subscriber.update(courseName);
        }
    }

    @Override
    public boolean isSubscriber(User subscriber){
        return this.subscribers.contains(subscriber);
    }

    @Override
    public void showSubscribers() {
        for(Student subscriber : this.subscribers){
            System.out.println(subscriber.getUsername());
        }
        System.out.println("Total Subscribers: " + this.subscribers.size());
    }
}
