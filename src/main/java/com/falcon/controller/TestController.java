package com.falcon.controller;


import com.falcon.model.DummyJson;
import com.falcon.producer.CustomerInfoPublisher;
import com.falcon.repository.DummyJsonRepository;
import com.falcon.config.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
//@Api(value = "This is a swagger test ",description = "This is the DummyJSON controller ")
public class TestController {
    @Autowired
    SocketHandler socketHandler;

    @Autowired
    DummyJsonRepository dummyJsonRepository;

    @Autowired
    private CustomerInfoPublisher redisPublisher;

//    @ApiOperation(value = "This method adds a new dummyJSON to mysql database and redis, you can check the output" +
//            "", response = Iterable.class)
    @Transactional
    @RequestMapping(value = "/name",method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity addDummyJson(@RequestBody()DummyJson givenDummyJson){
        DummyJson dummyJson = dummyJsonRepository.save(givenDummyJson);
        Gson  gson = new GsonBuilder().setPrettyPrinting().create();
        redisPublisher.publish(gson.toJson(dummyJson));
        return ResponseEntity.ok(dummyJson);
    }

//    @ApiOperation(value = "This method will return all of the persisted object in database and also pushes the result to listening websocket" +
//            "", response = Iterable.class)
    @RequestMapping(value = "/name",method= RequestMethod.GET, produces= "application/json")
    public @ResponseBody ResponseEntity findAll() {
        List<DummyJson> dummyJsons = null;
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            dummyJsons  = dummyJsonRepository.findAll();
            socketHandler.sendJsonObjectToWebSocketInGETMethod(gson.toJson(dummyJsons ));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(dummyJsons);
    }

}

