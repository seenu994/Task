package com.xyram.ticketingTool.exception;


/**
 * 
 * @author Pradeep Kumara K
 * 
 * @category RunTimeException
 * 
 * @implNote Handles RPM Application specific exception
 *
 */
public class TciketExpetion extends RuntimeException {

	private static final long serialVersionUID = 4027301750664826485L;

	public TciketExpetion(String s) {
		super(s);
	}
}
