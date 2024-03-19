package org.threeMusketeers.dliver_backend.Maps.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.threeMusketeers.dliver_backend.Maps.Pojo.VendorLocationDbObject;

import java.util.List;

@Repository
public interface VendorLocationDao extends JpaRepository<VendorLocationDbObject,Integer> {
}
