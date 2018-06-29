package net.ewant.redis;

public class RedisOperationException extends Exception {

    public RedisOperationException() {
        super();
    }

    public RedisOperationException(String message) {
        super(message);
    }

    public RedisOperationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisOperationException(Throwable cause) {
        super(cause);
    }

    protected RedisOperationException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
