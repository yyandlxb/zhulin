package cn.hlvan.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApplicationException extends RuntimeException {

    private String displayableMessage;

    public ApplicationException(String displayableMessage) {
        this.displayableMessage = displayableMessage;
    }

    public ApplicationException(String message, String displayableMessage) {
        super(message);
        this.displayableMessage = displayableMessage;
    }

}
