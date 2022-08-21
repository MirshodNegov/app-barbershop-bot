package com.example.barbershopqueuebotapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    public List<String> services;
    public int staff_id;
    public String datetime;
    public String chargeStatus;
    public CustomFields custom_fields;
    public int id;
}
