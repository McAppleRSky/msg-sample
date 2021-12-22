package tech.bergen._1_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import tech.bergen._2_service.DispatcherService;

@RestController
public class MessageController {

    @Autowired
    DispatcherService dispatcherService;

    @PostMapping(value = "/api/0.0.1/msg")
    public ResponseEntity<String> send(@RequestBody String body){
        int count = dispatcherService.sendMessage(body);
        return new ResponseEntity<>("Message sent: " + count, count > 0 ? HttpStatus.OK : HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
