package com.example.barbershopqueuebotapi;

import com.example.barbershopqueuebotapi.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Altegio {
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String TOKEN = "gtcwf654agufy25gsadh";
    private static final String GET_DAYS_URL = "https://b767416.alteg.io/api/v1/book_dates/" +
            "721155?staff_id=2094373";
    private static final String GET_HOURS_URL = "https://b767416.alteg.io/api/v1/book_times/" +
            "721155/2094373/";
    private static final String BOOK_ORDER_URL = "https://b767416.alteg.io/api/v1/book_record/721155";

    public Days getDays(String service) {
        URI url = UriComponentsBuilder.fromUriString(GET_DAYS_URL)
                .queryParam("date", new Date(System.currentTimeMillis()))
                .queryParam("service_ids[]", service)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Days.class).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public Hours[] getHours(String service, String date) {
        URI url = UriComponentsBuilder.fromUriString(GET_HOURS_URL + date + "?")
                .queryParam("service_ids[]", service)
                .build()
                .toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Object> httpEntity = new HttpEntity<>(null, headers);
        try {
            return restTemplate.exchange(url, HttpMethod.GET, httpEntity, Hours[].class).getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    public boolean bookOrder(String name, String phone, String email, String comment, String service, String dateTime) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        BookOrder bookOrder = new BookOrder();
        bookOrder.setFullname(name);
        bookOrder.setPhone(phone);
        bookOrder.setEmail(email);
        bookOrder.setComment(comment);
        List<Appointment> appointments = new ArrayList<>();
        Appointment appointment = new Appointment();
        appointment.setCustom_fields(null);
        appointment.setStaff_id(2094373);
        appointment.setChargeStatus("");
        appointment.setId(0);
        appointment.setDatetime(dateTime);
        List<String> services = new ArrayList<>();
        services.add(service);
        appointment.setServices((services));
        appointments.add(appointment);
        bookOrder.setAppointments(appointments);
        bookOrder.setBookform_id(767416);
        bookOrder.setMobile(true);
        bookOrder.setNotify_by_sms(1);
        bookOrder.setReferrer("https://l.instagram.com/");
        bookOrder.set_charge_required_priority(true);
        bookOrder.set_support_charge(false);
        bookOrder.setRedirect_url("https://b767416.alteg.io/company/721155/success-order/{recordId}/{recordHash}");
        List<AppointmentsCharge> appointmentsCharges = new ArrayList<>();
        AppointmentsCharge appointmentsCharge = new AppointmentsCharge();
        appointmentsCharge.setId(0);
        appointmentsCharge.setServices(new ArrayList<>());
        appointmentsCharge.setPrepaid(new ArrayList<>());
        appointmentsCharges.add(appointmentsCharge);
        bookOrder.setAppointments_charges(appointmentsCharges);

        HttpEntity<Object> httpEntity = new HttpEntity<>(bookOrder, headers);
        try {
            BookResult[] bookResults = restTemplate.exchange(BOOK_ORDER_URL, HttpMethod.POST, httpEntity, BookResult[].class).getBody();
            return bookResults.length != 0;
        } catch (Exception exception) {
            exception.printStackTrace();
            throw exception;
        }
    }

    //nothing
}

