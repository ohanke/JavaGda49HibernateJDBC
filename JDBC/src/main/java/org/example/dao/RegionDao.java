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
        if (resultSet.next()){
            String region_name = resultSet.getString("REGION_NAME");
            int region_idFromDB = resultSet.getInt("REGION_ID");
            preparedStatement.close();
            return new Region(region_name, regionId);
        }
        return null;
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
        preparedStatement.close();
        return regions;
    }

    public void delete(Integer regionId) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM REGIONS WHERE REGION_ID = ?");
        preparedStatement.setInt(1, regionId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void update(Region region) throws SQLException {
        String statement = "UPDATE REGIONS SET REGION_NAME = ? WHERE REGION_ID = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, region.getRegionName());
        preparedStatement.setInt(2, region.getRegionId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void trasactionTest(String name1, String name2) {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try{
            String statement = "UPDATE REGIONS SET REGION_NAME = ? WHERE REGION_ID = 3";
            PreparedStatement preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setString(1, name1);
            preparedStatement.executeUpdate();

            statement = "UPDATE REGIONS SET REGION_NAME = ? WHERE REGION_ID = 4";
            PreparedStatement preparedStatementId4 = connection.prepareStatement(statement);
            preparedStatementId4.setString(1, name2);
            preparedStatementId4.executeUpdate();


            connection.commit();
        } catch (SQLException e) {
            System.out.println(e);
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //        preparedStatement.close();
//        preparedStatementId4.close();
    }
}
