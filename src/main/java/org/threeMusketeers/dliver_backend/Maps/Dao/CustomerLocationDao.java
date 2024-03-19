package org.threeMusketeers.dliver_backend.Maps.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.threeMusketeers.dliver_backend.Maps.Pojo.CustomerLocationDbObject;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

@Repository
public interface CustomerLocationDao extends JpaRepository<CustomerLocationDbObject,Integer> {
}
