package manager;

import entity.BranchItem;
import entity.VehicleItem;
import entity.VehicleReservationItem;
import enums.VehicleStatus;
import exceptions.BranchAlreadyExistsException;
import exceptions.VehicleNotAddedException;
import exceptions.VehicleNotBookedException;
import service.IVehicleInventory;
import service.IVehicleRentalService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DriverManager {

    IVehicleRentalService vehicleRentalService;
    IVehicleInventory vehicleInventory;

    public DriverManager(IVehicleRentalService vehicleRentalService, IVehicleInventory vehicleInventory) {
        this.vehicleInventory = vehicleInventory;
        this.vehicleRentalService = vehicleRentalService;
    }

    public void addBranch(String branchId, String vehiclesItems) throws BranchAlreadyExistsException {
        BranchItem branchItem = new BranchItem(branchId,null);
        Set<String> vehicleSet = new HashSet<>();
        List<String> list = Arrays.asList(vehiclesItems.split(","));
        vehicleSet.addAll(list);
        vehicleRentalService.addBranch(branchItem,vehicleSet);
    }

    public void addVehicle(String branchId, String vehicleName, String vehicleId, String rentalPrice) throws VehicleNotAddedException{
        BranchItem branchItem = new BranchItem();
        branchItem.setBranchId(branchId);
        VehicleItem vehicleItem = new VehicleItem();
        vehicleItem.setVehicleName(vehicleName);
        vehicleItem.setVehicleId(vehicleId);
        vehicleItem.setHourlyRentalCost(Double.valueOf(rentalPrice));
        vehicleItem.setVehicleStatus(VehicleStatus.AVAILABLE);
        vehicleInventory.addVehicle(branchItem,vehicleItem);
    }

    public VehicleReservationItem bookVehicle(String branchId, String vehicleName, Integer startTime, Integer endTime) {
        BranchItem branchItem = new BranchItem();
        branchItem.setBranchId(branchId);
        VehicleItem vehicleItem = new VehicleItem();
        vehicleItem.setVehicleName(vehicleName);
        LocalDateTime start = LocalDate.now().atTime(startTime, 0);
        LocalDateTime end = LocalDate.now().atTime(endTime, 0);
        if(end.isBefore(start)) {
            throw  new VehicleNotBookedException();
        }
        return vehicleInventory.bookVehicle(branchItem,vehicleItem,start,end);
    }

    public List<String> displayAvailableVehicles(String branchId, Integer start, Integer end) {
        LocalDateTime startTime = LocalDate.now().atTime(start, 0);
        LocalDateTime endTime = LocalDate.now().atTime(end, 0);
        BranchItem branchItem = new BranchItem();
        branchItem.setBranchId(branchId);
        return vehicleInventory.getVehiclesByBranchAndTime(branchItem,startTime,endTime);
    }
}
