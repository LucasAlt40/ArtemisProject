package model.entity;

public class Request {
    private User userRequested;
    private User userSubmitted;
    private RequestStatus status;

    public Request() {}

    public User getUserRequested() {
        return userRequested;
    }

    public void setUserRequested(User userRequested) {
        this.userRequested = userRequested;
    }

    public User getUserSubmitted() {
        return userSubmitted;
    }

    public void setUserSubmitted(User userSubmitted) {
        this.userSubmitted = userSubmitted;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
