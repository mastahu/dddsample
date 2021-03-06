package com.reporting.db;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import se.citerus.dddsample.reporting.api.Handling;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HandlingRowMapper implements ParameterizedRowMapper<Handling> {

  @Override
  public Handling mapRow(ResultSet rs, int rowNum) throws SQLException {
    Handling handling = new Handling();
    handling.setCompletedOn(rs.getDate("completed_on"));
    handling.setLocation(rs.getString("location"));
    handling.setType(rs.getString("type"));
    handling.setVoyage(rs.getString("voyage_number"));
    return handling;
  }

}
