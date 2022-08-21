package com.example.barbershopqueuebotapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentsCharge {
    public int id;
    public ArrayList<Object> services;
    public ArrayList<Object> prepaid;
}
