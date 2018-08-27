package cn.hlvan.util;

import cn.hlvan.constant.InfoCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class Reply {

    private State state;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    private Reply(InfoCode infoCode) {
        state = new State();
        state.stateCode = infoCode.getStatus();
        state.stateMessage = infoCode.getMsg();
    }

    @Data
    private static class State {

        private int stateCode;

        private String stateMessage;

    }

    public Reply message(String message) {
        state.stateMessage = message;
        return this;
    }

    public Reply data(Object data) {
        this.data = data;
        return this;
    }

    public static Reply success() {
        return new Reply(InfoCode.SUCCESS);
    }

    public static Reply fail() {
        return new Reply(InfoCode.FAILURE);
    }

}
