package com.cibertec.app.dto;

import java.time.LocalTime;

import lombok.Value;

@Value
public class SlotDisponibleDTO {
	
	private Long idSlot;
	private LocalTime hora;
	
}
