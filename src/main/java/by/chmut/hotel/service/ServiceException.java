package by.chmut.hotel.service;

public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = -5247756223669926411L;

    public ServiceException() {
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(Exception e) {
        super(e);
    }

    public ServiceException(String message, Exception e) {
        super(message, e);
    }
}

