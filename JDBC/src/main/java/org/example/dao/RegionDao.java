package org.example.dao;

import lombok.AllArgsConstructor;
import org.example.models.Region;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class RegionDao {
    private Connection connection;

    public Region getRegionById(int regionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM REGIONS WHERE REGION_ID = ?");
        preparedStatement.setInt(1, regionId);
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        String region_name = resultSet.getString("REGION_NAME");
        int region_idFromDB = resultSet.getInt("REGION_ID");
        return new Region(region_name, regionId);
    }

    public List<Region> getAllRegions() throws SQLException {
        List<Region> regions = new ArrayList<>();

        PreparedStatement preparedStatement = connection.prepareStatement("SELECT REGION_ID, REGION_NAME FROM REGIONS");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            int region_id = resultSet.getInt("REGION_ID");
            String region_name = resultSet.getString("REGION_NAME");

            Region region = new Region(region_name, region_id);
            regions.add(region);
        }
        return regions;
    }
}
