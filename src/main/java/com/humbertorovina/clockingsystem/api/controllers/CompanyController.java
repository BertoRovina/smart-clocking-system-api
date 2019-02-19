package com.humbertorovina.clockingsystem.api.controllers;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.humbertorovina.clockingsystem.api.dtos.CompanyDto;
import com.humbertorovina.clockingsystem.api.entities.Company;
import com.humbertorovina.clockingsystem.api.response.Response;
import com.humbertorovina.clockingsystem.api.services.CompanyService;

@RestController
@RequestMapping("/api/companies")
@CrossOrigin(origins = "*")
public class CompanyController {

    private static final Logger log = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    private CompanyService companyService;

    public CompanyController() {
    }

    /**
     * Returns a company based on the Company Doc.
     *
     * @param companyDoc
     * @return ResponseEntity<Response<CompanyDto>>
     */
    @GetMapping(value = "/doc/{companyDoc}")
    public ResponseEntity<Response<CompanyDto>> searchByDoc(@PathVariable("companyDoc") String companyDoc) {
        log.info("Searching Company by Doc: {}", companyDoc);
        Response<CompanyDto> response = new Response<>();
        Optional<Company> company = companyService.searchByDoc(companyDoc);

        if (!company.isPresent()) {
            log.info("Company not found based on the Company Doc: {}", companyDoc);
            response.getErrors().add("Company not found based on the Company Doc " + companyDoc);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertCompanyDto(company.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Fills DTO with a company data.
     *
     * @param company
     * @return CompanyDto
     */
    private CompanyDto convertCompanyDto(Company company) {
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setCompanyDoc(company.getDoc());
        companyDto.setCompanyName(company.getCompanyName());

        return companyDto;
    }

}
