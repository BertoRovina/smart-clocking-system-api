package com.humbertorovina.clockingsystem.api.dtos;

public class CompanyDto {

    private Long id;
    private String companyName;
    private String companyDoc;

    public CompanyDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getcompanyDoc() {
        return companyDoc;
    }

    public void setCompanyDoc(String companyDoc) {
        this.companyDoc = companyDoc;
    }

    @Override
    public String toString() {
        return "CompanyaDto [id=" + id + ", companyName=" + companyName + ", companyDoc=" + companyDoc + "]";
    }

}
