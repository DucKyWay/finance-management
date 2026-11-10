package com.suphawinee.financeManagement.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Plates {
    final List<Plate> plateList = new ArrayList<>();
    private int nextId = 0;

    public void addPlate(String name, double price, String fileName) {
        plateList.add(new Plate(nextId++, name, price, fileName));
    }

    public List<Plate> findAllPlates() {
        plateList.sort(Comparator.comparingDouble(Plate::getPrice));
        return plateList;
    }

    public Plate findPlateById(int id) {
        for(Plate p : plateList) {
            if(p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    public Plate updatePlateNameById(int id, String name) {
        Plate plate = findPlateById(id);
        plate.setName(name);
        return plate;
    }

    public Plate updatePlatePriceById(int id, double price) {
        Plate plate = findPlateById(id);
        plate.setPrice(price);
        return plate;
    }

    public Plate updatePlateFileNameById(int id, String fileName) {
        Plate plate = findPlateById(id);
        plate.setFileName(fileName);
        return plate;
    }

    public Plate removePlateById(int id) {
        Plate plate = findPlateById(id);
        if (plate != null) plateList.remove(plate);
        return plate;

    }

    public double calculateTotal() {
        double total = 0;
        for (Plate p : plateList) {
            total += p.getSubTotal();
        }
        return total * 1.1;
    }

    public double calculateServiceCharge() {
        double total = 0;
        for (Plate p : plateList) {
            total += p.getSubTotal();
        }
        return total * 0.1;
    }
}
