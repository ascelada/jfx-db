package services.impl;

import database.DB;
import database.DbException;
import services.DepartmentDao;
import entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, department.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    department.setId(id);

                }
                DB.closeResultSet(rs);
            }


        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Department department) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
            st.setString(1, department.getName());
            st.setInt(2, department.getId());

            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

    }

    @Override
    public void removeById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM department WHERE Id =?");
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department WHERE Id = ? ");
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department department = new Department();
                department.setId(rs.getInt("Id"));
                department.setName(rs.getString("Name"));
                return department;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT * FROM department ORDER BY Name");
            rs = st.executeQuery();

            List<Department> list = new ArrayList<>();

            while (rs.next()) {
                Department department = new Department();
                department.setName(rs.getString("Name"));
                department.setId(rs.getInt("Id"));
                list.add(department);
            }
            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }
}
