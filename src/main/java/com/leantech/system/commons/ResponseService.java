package com.leantech.system.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseService {
	
	private int code;

	private String message;

	private Object datos;

	public ResponseService(int code, String message) {

		this.code = code;
		this.message = message;
	}

}
