package services;

import database.DB;
import services.impl.DepartmentDaoJDBC;
import services.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getConnection());
    }
    public static DepartmentDao createDepartmentDao(){
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
