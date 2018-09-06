package com.devglan.controller;


import com.devglan.model.DummyJson;
import com.devglan.producer.CustomerInfoPublisher;
import com.devglan.repository.DummyJsonRepository;
import com.devglan.config.SocketHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@Api(value = "This is a swagger test ",description = "This is the DummyJSON controller ")
public class TestController {
    @Autowired
    SocketHandler socketHandler;

    @Autowired
    DummyJsonRepository dummyJsonRepository;

    @Autowired
    private CustomerInfoPublisher redisPublisher;

    @ApiOperation(value = "This method adds a new account to mysql database and its corresponding balance to redis" +
            "", response = Iterable.class)
    @Transactional
    @RequestMapping( method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity addDummyJson(@RequestBody()DummyJson givenDummyJson){
        DummyJson dummyJson = dummyJsonRepository.save(givenDummyJson);
        redisPublisher.publish(dummyJson);
        return ResponseEntity.ok(dummyJson);
    }



    @GetMapping("/name")
    public String findAll() {
        try {
           // socketHandler.afterConnectionEstablished("xXXXXXXXXXXXXXXX from rest");
            redisPublisher.publish(new DummyJson("test1"));
            redisPublisher.publish(new DummyJson("test2"));
            redisPublisher.publish(new DummyJson("test3"));
            Thread.sleep(50);
            redisPublisher.publish(new DummyJson("test4"));
            redisPublisher.publish(new DummyJson("test5"));
            DummyJson dummyJson = new DummyJson("testName");
            dummyJsonRepository.save(dummyJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "What the hellllllllllllllll";
    }

}

