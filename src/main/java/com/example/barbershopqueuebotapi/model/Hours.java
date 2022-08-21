package com.example.barbershopqueuebotapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Hours {
    public String time;
    public int seance_length;
    public int sum_length;
    public Date datetime;
}
