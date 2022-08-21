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
public class Days {
    public BookingDays booking_days;
    public ArrayList<String> booking_dates;
    public WorkingDays working_days;
    public ArrayList<String> working_dates;
}
