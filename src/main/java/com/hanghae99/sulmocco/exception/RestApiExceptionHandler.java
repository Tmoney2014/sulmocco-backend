package com.hanghae99.sulmocco.exception;

import io.sentry.Sentry;
import io.sentry.SentryEvent;
import io.sentry.SentryLevel;
import io.sentry.protocol.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestControllerAdvice
public class RestApiExceptionHandler {

//    ResponseEntity를 생성하기 위한 방법에는 생성자를 사용하는 방법과 static 메소드를 사용하는 방법으로 제공된다.
//    static 메소드가 조금 더 가독성이 좋고, 메모리를 덜  사용하는 방법
//    정적 팩토리 메소드를 사용하는게 무조건적으로 좋진 않지만, 미리 구현되어있다면, 사용해보는것도 좋겠죠?
//    https://thalals.tistory.com/268
    @ExceptionHandler(value = { IllegalArgumentException.class, ResponseStatusException.class}) // 여러개의 Exception 추가할 수 있다.
    public ResponseEntity<Object> handleApiRequestException(IllegalArgumentException ex) {

        Message message = new Message();
        message.setMessage("Exception 발생");

        SentryEvent event = new SentryEvent();
        event.setTag("Advice", UUID.randomUUID().toString());
        event.setLevel(SentryLevel.WARNING);
        event.setMessage(message);

        Sentry.captureEvent(event);

        Sentry.captureException(ex);

        RestApiException restApiException = new RestApiException();
        restApiException.setResponse(false);
        restApiException.setMessage(ex.getMessage());

        return new ResponseEntity(
                restApiException,
                HttpStatus.BAD_REQUEST
        );
    }
}
