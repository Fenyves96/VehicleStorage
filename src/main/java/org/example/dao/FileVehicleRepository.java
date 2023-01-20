package org.example.dao;

import org.example.entity.Vehicle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileVehicleRepository implements VehicleRepository {

    public FileVehicleRepository(){
        clearFile();
    }

    private void clearFile() {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("vehicles.txt");
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final String SEPARATOR = ";";

    @Override
    public void saveVehicle(Vehicle vehicle) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("vehicles.txt", true);
            fileWriter.write(generateVehicleRow(vehicle) + System.lineSeparator());
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String generateVehicleRow(Vehicle vehicle) {
        return vehicle.getRegistrationNumber() + SEPARATOR +
                vehicle.getMake() + SEPARATOR +
                vehicle.getModel() + SEPARATOR +
                vehicle.getNumberOfSeats() + SEPARATOR +
                vehicle.getVehicleType();
    }

    @Override
    public Vehicle findVehicleByRegistrationNumber(String registrationNumber) {
        String line;
        Vehicle result = null;
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader("vehicles.txt"));
            while((line = bufferreader.readLine()) != null){
                Vehicle vehicle = parseVehicle(line);
                if(vehicle.getRegistrationNumber().equals(registrationNumber))
                    result = vehicle;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private Vehicle parseVehicle(String line) {
        String [] vehicleAttributes = line.split(SEPARATOR);
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(vehicleAttributes[0]);
        vehicle.setMake(vehicleAttributes[1]);
        vehicle.setModel(vehicleAttributes[2]);
        vehicle.setNumberOfSeats(Integer.parseInt(vehicleAttributes[3]));
        vehicle.setVehicleType(vehicleAttributes[4]);
        return vehicle;
    }
}