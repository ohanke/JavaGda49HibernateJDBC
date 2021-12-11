package org.example.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.example.models.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

public class CountriesDao {
    private Connection connection;

    public Country getCountryById(String countryId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM COUNTRIES WHERE COUNTRY_ID = ?");
        preparedStatement.setString(1, countryId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()){
            String country_idFromDB = resultSet.getString("COUNTRY_ID");
            String country_name = resultSet.getString("COUNTRY_NAME");
            int region_id = resultSet.getInt("REGION_ID");
            return new Country(country_idFromDB, country_name, region_id);
        }
        return null;
    }

    public List<Country> getAllCountries() throws SQLException {
        List<Country> countries = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM COUNTRIES");
        ResultSet countriesResultSet = preparedStatement.executeQuery();

        while (countriesResultSet.next()){
            String country_id = countriesResultSet.getString("COUNTRY_ID");
            String country_name = countriesResultSet.getString("COUNTRY_NAME");
            int region_id = countriesResultSet.getInt("REGION_ID");

            Country country = new Country(country_id, country_name, region_id);
            countries.add(country);
        }
        return countries;
    }

    public void delete(String countryId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM COUNTRIES WHERE COUNTRY_ID = ?");
        preparedStatement.setString(1, countryId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void update(Country country) throws SQLException {
        String statement = "";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, country.getCountryId());
        preparedStatement.setString(2, country.getCountryName());
        preparedStatement.setInt(3, country.getRegionId());
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
}
