package com.humbertorovina.clockingsystem.api.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.humbertorovina.clockingsystem.api.entities.Employee;
import com.humbertorovina.clockingsystem.api.enums.ProfileEnum;

public class JwtUserFactory {

    private JwtUserFactory() {
    }

    /**
     * Convert and generates JwtUser based on employee data.
     *
     * @param employee
     * @return JwtUser
     */
    public static JwtUser create(Employee employee) {
        return new JwtUser(employee.getId(), employee.getEmail(), employee.getPassword(),
                mapToGrantedAuthorities(employee.getProfile()));
    }

    /**
     * Convert the user profile used to be utilized by Spring Security.
     *
     * @param profileEnum
     * @return List<GrantedAuthority>
     */
    private static List<GrantedAuthority> mapToGrantedAuthorities(ProfileEnum profileEnum) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority(profileEnum.toString()));
        return authorities;
    }

}