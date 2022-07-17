package service;

import entity.BranchItem;
import entity.VehicleItem;
import entity.VehicleReservationItem;
import exceptions.ReservationNotFoundException;
import exceptions.VehicleNotAddedException;

import java.time.LocalDateTime;
import java.util.List;

public interface IVehicleInventory {

    List<String> getVehiclesByBranchAndTime(BranchItem branchId, LocalDateTime startTime, LocalDateTime endTime) ;

    List<VehicleItem> getVehiclesByBranchAndVehicleName(BranchItem branchItem, VehicleItem vehicleItem) ;

    void addVehicle(final BranchItem branch, final VehicleItem vehicleItem) throws VehicleNotAddedException;

    VehicleReservationItem bookVehicle(BranchItem branch, VehicleItem vehicleItem, LocalDateTime startTime, LocalDateTime endTime);

    void returnVehicle(final BranchItem branch, final VehicleItem vehicleItem) throws ReservationNotFoundException;
}
