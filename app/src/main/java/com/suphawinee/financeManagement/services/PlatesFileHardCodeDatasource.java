package com.suphawinee.financeManagement.services;

import com.suphawinee.financeManagement.models.Plates;

public class PlatesFileHardCodeDatasource implements Datasource<Plates>{

    @Override
    public Plates readData() {
        Plates plates = new Plates();
        plates.addPlate("Red", 40, "plate_40");
        plates.addPlate("Blue", 50, "plate_50");
        plates.addPlate("Green", 60, "plate_60");
        plates.addPlate("Yellow", 70, "plate_70");
        plates.addPlate("Brown", 80, "plate_80");
        plates.addPlate("Pink", 90, "plate_90");
        plates.addPlate("Gray", 100, "plate_100");
        plates.addPlate("White", 120, "plate_120");
        plates.addPlate("Red Gold", 140, "plate_140");
        plates.addPlate("Gold", 150, "plate_150");
        plates.addPlate("Light Blue", 160, "plate_160");
        plates.addPlate("Black", 180, "plate_180");
        return plates;
    }

    @Override
    public void writeData(Plates data) {

    }
}
