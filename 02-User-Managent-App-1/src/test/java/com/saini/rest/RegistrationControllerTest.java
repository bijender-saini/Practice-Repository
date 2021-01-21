package com.saini.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.saini.restcontroller.RegistrationController;
import com.saini.service.UserService;
@WebMvcTest(value=RegistrationController.class)
public class RegistrationControllerTest {
	
	@Autowired
	private MockMvc mockmvc;
	@MockBean
	private UserService userservice;
	
	@Test
	public void test_getcountryName() throws Exception
	{
	
		
		Map<Integer,String>map=new HashMap<>();
		map.put(1, "India");
		map.put(2, "USA");
		map.put(3, "Russia");
		map.put(4, "France");
		when(userservice.findCountries()).thenReturn(map);
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/getCountry");
		MvcResult mvcResult = mockmvc.perform(requestBuilder).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		
	}

}
