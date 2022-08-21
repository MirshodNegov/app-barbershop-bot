package com.example.barbershopqueuebotapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResult {
    public int id;
    public int record_id;
    public String record_hash;
}
