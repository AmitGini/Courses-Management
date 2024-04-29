public interface NotificationService {

    public void addSubscriber(User subscriber);

    public void removeSubscriber(User subscriber);

    public void updateAll(String message);

    public boolean isSubscriber(User subscriber);

    public void showSubscribers();
}
