package com.humbertorovina.clockingsystem.api.dtos;

import java.util.Optional;


public class ClockingDto {

    private Optional<Long> id = Optional.empty();
    private String date;
    private String type;
    private String description;
    private String localization;
    private Long employeeId;

    public ClockingDto() {
    }

    public void setId(Optional<Long> id) {
        this.id = id;
    }

    public Optional<Long> getId() {
        return id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public String getLocalization() {
        return localization;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }


    @Override
    public String toString() {
        return "ClockingDto [id=" + id + ", date=" + date + ", type=" + type + ", description=" + description
                + ", localization=" + localization + ", employeeId=" + employeeId + "]";
    }
}
