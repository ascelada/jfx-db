package services;

import entities.Seller;

import java.util.List;

public interface SellerDao {

    void insert(Seller obj);

    void update(Seller obj);

    void removeById(Integer id);

    Seller findById(Integer id);

    List<Seller> findAll();
}
