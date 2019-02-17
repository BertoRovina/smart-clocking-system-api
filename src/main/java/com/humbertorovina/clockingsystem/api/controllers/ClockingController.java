package com.humbertorovina.clockingsystem.api.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.humbertorovina.clockingsystem.api.dtos.ClockingDto;
import com.humbertorovina.clockingsystem.api.entities.Clocking;
import com.humbertorovina.clockingsystem.api.response.Response;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;
import com.humbertorovina.clockingsystem.api.services.ClockingService;
import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.enums.TypeEnum;

@RestController
@RequestMapping("/api/clocking")
@CrossOrigin(origins = "*")
public class ClockingController {

    private static final Logger log = LoggerFactory.getLogger(ClockingController.class);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private ClockingService clockingService;

    @Autowired
    private EmployeeService employeeService;

    @Value("${pagination.qtt_per_page}")
    private int qttPerPage;

    public ClockingController() {
    }

    /**
     * Returns an employee clocking list
     *
     * @param employeeId
     * @param page
     * @param ord
     * @param dir
     * @return ResponseEntity<Response<ClockingDto>>
     */
    @GetMapping(value = "/employee/{employeeId}")
    public ResponseEntity<Response<Page<ClockingDto>>> listByEmployeeId(
            @PathVariable("employeeId") Long employeeId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "ord", defaultValue = "id") String ord,
            @RequestParam(value = "dir", defaultValue = "DESC") String dir){
        log.info("Searching clocking by employee ID: {}, page: {}", employeeId, page);
        Response<Page<ClockingDto>> response = new Response<>();

        PageRequest pageRequest = PageRequest.of(page, this.qttPerPage, Direction.valueOf(dir), ord);
        Page<Clocking> clocking = this.clockingService.searchEmployeeById(employeeId, pageRequest);
        Page<ClockingDto> clockingDto = clocking.map(clockin -> this.convertClockingDto(clockin));

        response.setData(clockingDto);
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a clocking by its ID.
     *
     * @param id
     * @return ResponseEntity<Response<ClockingDto>>
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Response<ClockingDto>> listById(@PathVariable("id") Long id) {
        log.info("Searching clocking by ID: {}", id);
        Response<ClockingDto> response = new Response<>();
        Optional<Clocking> clocking = this.clockingService.searchById(id);

        if (!clocking.isPresent()) {
            log.info("Search did not return a result for the ID: {}", id);
            response.getErrors().add("Search did not return a result for the id " + id);
            return ResponseEntity.badRequest().body(response);
        }

        response.setData(this.convertClockingDto(clocking.get()));
        return ResponseEntity.ok(response);
    }

    /**
     * Adds a new clocking
     *
     * @param clockingDto
     * @param result
     * @return
     * @throws ParseException
     */
    @PostMapping
    public ResponseEntity<Response<ClockingDto>> add(@Valid @RequestBody ClockingDto clockingDto,
                                                             BindingResult result) throws ParseException {
        log.info("Adding clock: {}", clockingDto.toString());
        Response<ClockingDto> response = new Response<>();
        validateEmployee(clockingDto, result);
        Clocking clocking = this.convertDtoToClocking(clockingDto, result);

        if (result.hasErrors()) {
            log.error("Error trying to validate clocking: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        clocking = this.clockingService.persist(clocking);
        response.setData(this.convertClockingDto(clocking));
        return ResponseEntity.ok(response);
    }

    /**
     * Update an existing clocking
     *
     * @param id
     * @param clockingDto
     * @return
     * @throws ParseException
     */
    @PutMapping (value = "/{id}")
    public ResponseEntity<Response<ClockingDto>> update(@PathVariable("id") Long id,
                                        @Valid @RequestBody ClockingDto clockingDto, BindingResult result) throws ParseException{
        log.info("Updating clocking: {}", clockingDto.toString());
        Response<ClockingDto> response = new Response<>();
        validateEmployee(clockingDto, result);
        clockingDto.setId(Optional.of(id));
        Clocking clocking = this.convertDtoToClocking(clockingDto, result);

        if (result.hasErrors()){
            log.error("Error validating the clocking: {}", result.getAllErrors());
            result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        clocking = this.clockingService.persist(clocking);
        response.setData(this.convertClockingDto(clocking));
        return ResponseEntity.ok(response);
    }

    /**
     * Removes an existing clocking
     *
     * @param id
     * @return ResponseEntity<Response<Clocking>>
     */
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Response<String>> remove(@PathVariable("id") Long id){
        log.info("Removing the clocking: {}", id);
        Response<String> response = new Response<>();
        Optional<Clocking> clocking = this.clockingService.searchById(id);

        if (!clocking.isPresent()){
            log.info("Error trying to remove the clocking, due to invalid id: {}", id);
            response.getErrors().add("Error while removing clocking. No clocking with id was found. " + id);
            return ResponseEntity.badRequest().body(response);
        }

        this.clockingService.remover(id);
        return ResponseEntity.ok(new Response<>());
    }

    /**
     * Validates employee based on ID
     *
     *
     * @param clockingDto
     * @param result
     */
    private void validateEmployee(ClockingDto clockingDto, BindingResult result) {
        if (clockingDto.getEmployeeId() == null) {
            result.addError(new ObjectError("employee", "Employee not given."));
            return;
        }

        log.info("Validating employee id {}: ", clockingDto.getEmployeeId());

        Optional<Employee> employee = this.employeeService.searchById(clockingDto.getEmployeeId());
        if (!employee.isPresent()) {
            result.addError(new ObjectError("employee", "Employee not found. Nonexistent ID."));
        }
    }

    /**
     * Converts an entity clocking to its Dto
     *
     * @param clocking
     * @return
     */
    private ClockingDto convertClockingDto(Clocking clocking) {
        ClockingDto clockingDto = new ClockingDto();
        clockingDto.setId(Optional.of(clocking.getId()));
        clockingDto.setDate(this.dateFormat.format(clocking.getDate()));
        clockingDto.setType(clocking.getType().toString());
        clockingDto.setDescription(clocking.getDescription());
        clockingDto.setLocalization(clocking.getLocalization());
        clockingDto.setEmployeeId(clocking.getEmployee().getId());

        return clockingDto;
    }
    /**
     * Convert a ClockingDto to its entity Clocking
     *
     * @param clockingDto
     * @param result
     * @return
     * @throws ParseException
     */
    private Clocking convertDtoToClocking(ClockingDto clockingDto, BindingResult result) throws ParseException{
        Clocking clocking = new Clocking();

        if (clockingDto.getId().isPresent()) {
            Optional<Clocking> clock = this.clockingService.searchById(clockingDto.getId().get());
            if (clock.isPresent()) {
                clocking = clock.get();
            } else {
                result.addError(new ObjectError("clocking", "Clocking not found."));
            }
        } else {
            clocking.setEmployee(new Employee());
            clocking.getEmployee().setId(clockingDto.getEmployeeId());
        }

        clocking.setDescription(clockingDto.getDescription());
        clocking.setLocalization(clockingDto.getLocalization());
        clocking.setDate(this.dateFormat.parse(clockingDto.getDate()));

        if (EnumUtils.isValidEnum(TypeEnum.class, clockingDto.getType())) {
            clocking.setType(TypeEnum.valueOf(clockingDto.getType()));
        } else {
            result.addError(new ObjectError("type", "Invalid type."));
        }

        return clocking;
    }
}