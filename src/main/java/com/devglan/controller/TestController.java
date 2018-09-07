package com.devglan.controller;


import com.devglan.model.DummyJson;
import com.devglan.producer.CustomerInfoPublisher;
import com.devglan.repository.DummyJsonRepository;
import com.devglan.config.SocketHandler;
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
@Api(value = "This is a swagger test ",description = "This is the DummyJSON controller ")
public class TestController {
    @Autowired
    SocketHandler socketHandler;

    @Autowired
    DummyJsonRepository dummyJsonRepository;

    @Autowired
    private CustomerInfoPublisher redisPublisher;

    @ApiOperation(value = "This method adds a new dummyJSON to mysql database and redis" +
            "", response = Iterable.class)
    @Transactional
    @RequestMapping( method= RequestMethod.POST, consumes = "application/json")
    public @ResponseBody
    ResponseEntity addDummyJson(@RequestBody()DummyJson givenDummyJson){
        DummyJson dummyJson = dummyJsonRepository.save(givenDummyJson);
        Gson  gson = new GsonBuilder().setPrettyPrinting().create();
        redisPublisher.publish(gson.toJson(dummyJson));
        return ResponseEntity.ok(dummyJson);
    }



    @GetMapping("/name")
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

