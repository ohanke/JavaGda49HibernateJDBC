package org.example;

import org.example.dao.CountriesDao;
import org.example.dao.RegionDao;
import org.example.models.Country;
import org.example.models.Region;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main( String[] args ) throws Exception {
//        DbCreator dbCreator = new DbCreator();
//        dbCreator.createAndLoadData();
        ConnectionManager connectionManager = new ConnectionManager();
        Connection connection = connectionManager.getConnection();
//        Statement statement = connection.createStatement();

        RegionDao regionDao = new RegionDao(connection);
        CountriesDao countriesDao = new CountriesDao(connection);

        List<Region> regions = regionDao.getAllRegions();
        regions.forEach(System.out::println);

        List<Country> countries = countriesDao.getAllCountries();
        countries.forEach(System.out::println);

        Region region = regionDao.getRegionById(2);
        System.out.println("Region by id 2: " + region);

        Country country = countriesDao.getCountryById("KW");
        System.out.println("Country by id 2: " + country);

//        preparedStatement.close();
        connection.close();
    }
}
