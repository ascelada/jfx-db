package services.impl;

import database.DB;
import database.DbException;
import services.SellerDao;
import entities.Department;
import entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {
    private Connection conn;



    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("INSET INTO seller" + "(Name,Email,BirthDate, BaseSalary, DepartmentId)" + "VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
                }
                DB.closeResultSet(rs);
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());

        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller seller) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("UPDATE seller" + " SET Name =?, Email = ? , BirthDate = ?, BaseSalary = ?, DepartmentId =?" + "WHERE Id = ?");

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);

        }


    }

    @Override
    public void removeById(Integer id) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller WHERE Id = ?");
            st.setInt(1, id);
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id " + "WHERE seller.Id = ? ");

            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, department);
                return seller;
            }
            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    public Seller findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id " + "WHERE DepartmentId = ? " + "ORDER BY Name ");

            st.setInt(1, department.getId());
            rs = st.executeQuery();

            if (rs.next()) {
                Department dep = instantiateDepartment(rs);
                Seller seller = instantiateSeller(rs, dep);
                return seller;
            }
            return null;

        } catch (SQLException e) {

            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department " + "ON seller.DepartmentId = department.Id " + "ORDER BY Name ");
            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();


            while (rs.next()) {
                Department department = map.get(rs.getInt("DepartmentId"));

                if (department == null) {
                    department = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), department);

                }
                Seller seller = instantiateSeller(rs, department);
                list.add(seller);
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
