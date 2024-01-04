package com.example.envirofx;

import javafx.util.StringConverter;
import model.Vehicle;

public class VehicleStringConverter extends StringConverter<Vehicle> {
    @Override
    public String toString(Vehicle vehicle) {
        return vehicle.getType(); // Mengubah objek Vehicle menjadi string
    }

    @Override
    public Vehicle fromString(String string) {
        // Jika diperlukan, Anda dapat mengimplementasikan konversi dari string kembali ke objek Vehicle di sini
        return null; // Misalnya, Anda dapat mengembalikan objek Vehicle yang sesuai dengan string
    }
}
