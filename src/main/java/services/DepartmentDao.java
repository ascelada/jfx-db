package services;

import entities.Department;

import java.util.List;

public interface DepartmentDao {
    void insert(Department obj);

    void update(Department obj);

    void removeById(Integer id);

    Department findById(Integer id);

    List<Department> findAll();
}
