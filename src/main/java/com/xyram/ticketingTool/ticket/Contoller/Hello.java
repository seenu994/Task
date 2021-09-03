package com.xyram.ticketingTool.ticket.Contoller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {

	@RequestMapping({ "/hello" })
	public String firstPage() {
		return "Hello World";
	}

}