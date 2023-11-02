package com.muriloCruz.ItGames.controller;

import com.muriloCruz.ItGames.entity.Enterprise;
import com.muriloCruz.ItGames.entity.enums.Status;
import com.muriloCruz.ItGames.service.impl.EnterpriseServiceImpl;
import com.muriloCruz.ItGames.service.proxy.EnterpriseServiceProxy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EnterpriseControllerTest {

    @InjectMocks
    EnterpriseController controller;

    @Mock
    private EnterpriseServiceProxy service;

    @Autowired
    MockMvc mockMvc;

    private Enterprise enterpriseTest;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .alwaysDo(print()).build();
        enterpriseTest = new Enterprise(1, "EnterpriseTest1", Status.A);
    }

    @Test
    @DisplayName("Should post enterprise from DB")
    void postEnterpriseCase1() throws Exception {
        enterpriseTest.setId(null);
        when(service.insert(any())).thenReturn(enterpriseTest);

        mockMvc.perform(post("/enterprise")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":null,\"name\":\"EnterpriseTest1\",\"status\":\"A\"}")
                .accept(MediaType.APPLICATION_JSON))
        .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("EnterpriseTest1"))
                .andExpect(content().json("[{'id':1,name:'EnterpriseTest1', status:'A'"));


    }


}