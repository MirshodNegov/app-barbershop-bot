package com.example.barbershopqueuebotapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class BookOrder {
    public String fullname;
    public String phone;
    public String email;
    public String comment;
    public List<Appointment> appointments;
    public int bookform_id;
    public boolean isMobile;
    public int notify_by_sms;
    public String referrer;
    public boolean is_charge_required_priority;
    public boolean is_support_charge;
    public List<AppointmentsCharge> appointments_charges;
    public String redirect_url;
}
