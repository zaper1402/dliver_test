package org.threeMusketeers.dliver_backend.Inventory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeMusketeers.dliver_backend.Maps.Service.VendorsLocationService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    OdooService odooService;



    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate() {
        try{
            return ResponseEntity.ok(odooService.authenticate());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        try{
            return ResponseEntity.ok(odooService.listRecords());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/getData")
    public ResponseEntity<?> getData() {
        try{
            return ResponseEntity.ok(odooService.readRecords());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/inspectTable")
    public ResponseEntity<?> inspectTable() {
        try{
            return ResponseEntity.ok(odooService.inspectTable());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/searchRead")
    public ResponseEntity<?> searchRead() {
        try{
            return ResponseEntity.ok(odooService.searchRead());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/create")
    public ResponseEntity<?> create() {
        try{
            return ResponseEntity.ok(odooService.createRecords());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/update")
    public ResponseEntity<?> update() {
        try{
            return ResponseEntity.ok(odooService.updateRecord());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }

    @GetMapping("/delete")
    public ResponseEntity<?> delete() {
        try{
            return ResponseEntity.ok(odooService.deleteRecord());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getLocalizedMessage());
        }
    }
}
