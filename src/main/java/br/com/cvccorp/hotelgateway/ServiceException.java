package br.com.cvccorp.hotelgateway;

public class ServiceException extends RuntimeException {
    public ServiceException(Exception ex) {
        super(ex);
    }

    public ServiceException(String errorMsg) {
        super(errorMsg);
    }
}