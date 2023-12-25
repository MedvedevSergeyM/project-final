package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.profile.internal.ProfileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProfileRestControllerTest extends AbstractControllerTest {

    @Autowired
    ProfileMapper mapper;

    @Test
    @WithUserDetails(value = GUEST_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void updateInvalid() throws Exception {
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getInvalidTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void updateUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}