package com.humbertorovina.clockingsystem.api.security.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.security.JwtUserFactory;
import com.humbertorovina.clockingsystem.api.services.EmployeeService;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private EmployeeService employeeService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> employee = employeeService.searchByEmail(username);

        if (employee.isPresent()) {
            return JwtUserFactory.create(employee.get());
        }

        throw new UsernameNotFoundException("Email not found");
    }

}
