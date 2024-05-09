package com.example.demo.controller;

import com.example.demo.controllers.UserController;
import com.example.demo.entities.User;
import com.example.demo.models.UserDto;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.Arrays;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    private User user;
    private UserDto userDto;
    private UserDto userDto2;

    public UserControllerTests() {
        this.userDto = UserDto.builder()
                .id(1)
                .email("fatih@trendology.co")
                .firstName("hüseyin fatih")
                .lastName("kazan")
                .phone("5399999999")
                .build();
    }

    /**
     * This is just for mock data. We can use this data for checking what do endpoints return correct response.
    *  */
    @BeforeEach
    public void init(){
        user = User.builder()
                .email("fatih@trendology.co")
                .firstName("hüseyin fatih")
                .lastName("kazan")
                .phone("5399999911")
                .build();
        userDto = UserDto.builder()
                .id(1)
                .email("fatih.kazan@trendology.co")
                .password("qwer1234")
                .firstName("hüseyin fatih")
                .lastName("kazan")
                .phone("5399999999")
                .build();

        userDto2 = UserDto.builder()
                .id(2)
                .email("huseyin@trendology.co")
                .password("1234qwer")
                .firstName("hüseyin")
                .lastName("kazan")
                .phone("1111111111")
                .build();
    }

/**
 * This test using for create user endpoint.
 * Check status
 * */
    @Test
    @DisplayName("User created successfully")
    public void userCreateController() throws Exception{
        given(userService.saveUser(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));


        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    /**
     * This test for checking data fields.
     */
    @Test
    @DisplayName("Created user's all fields are correct")
    public void checkData() throws Exception {
        given(userService.saveUser(ArgumentMatchers.any())).willAnswer((invocation) -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonObj = ow.writeValueAsString(userDto);

        response.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    /** Get User Controller calls userService.findAllUsers then findAllUsers returns users.
     * In this test, users endpoint's response and findAllUser's response have to match.
    */
    @Test
    @DisplayName("User getting successfully")
    public void userGetController() throws Exception{
        when(userService.findAllUsers()).thenReturn(Arrays.asList(userDto));

        ResultActions response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonObj = ow.writeValueAsString(Arrays.asList(userDto));


        response
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    /**
     * This test for PUT Users endpoint. And we want to check response status
     * */
    @Test
    @DisplayName("User updated successfully")
    public void userPutController() throws Exception {
        given(userService.updateUser(ArgumentMatchers.any())).willReturn(user);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/users/1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonObj = ow.writeValueAsString(user);

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * This test for what do fields updated successfully
     * */
    @Test
    @DisplayName("User fields updated correctly")
    public void userPutDataController() throws Exception {
        given(userService.updateUser(ArgumentMatchers.any())).willReturn(user);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/users/1/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String jsonObj = ow.writeValueAsString(user);

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonObj));
    }

    /**
     * This test for checking to does exception block run successfully
     * */
    @Test
    @DisplayName("User updating exception block run successfully")
    public void userPutExceptionController() throws Exception {
        given(userService.updateUser(ArgumentMatchers.any())).willReturn(user);

        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/users/null/edit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
