package com.selfJwtTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.selfJwt.controller.PrivilegeController;
import com.selfJwt.model.Privilege;
import com.selfJwt.service.PrivilegesService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = PrivilegeControllerTest.class)
public class PrivilegeControllerTest {

	@Mock
	private PrivilegesService service;

	@InjectMocks
	private PrivilegeController controller;

	private MockMvc mockMvc;

	@InjectMocks
	private Gson gson;

	@Before
	public void set() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void findById() throws Exception {
		Privilege privilege = new Privilege();
		privilege.setName("READ");
		privilege.setId("");
		Mockito.when(service.getById(Mockito.anyString())).thenReturn(privilege);
		String json = gson.toJson(privilege);
		MockHttpServletRequestBuilder accept = MockMvcRequestBuilders.get("/privilege/1")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		MvcResult andReturn = mockMvc.perform(accept).andReturn();
		JSONAssert.assertEquals(json, andReturn.getResponse().getContentAsString(), false);
	}
}
