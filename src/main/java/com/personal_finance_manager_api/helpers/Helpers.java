package com.personal_finance_manager_api.helpers;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Component
public class Helpers {
    public LocalDate formatDate(String date) {
        return LocalDate.parse(date);
    }
}
