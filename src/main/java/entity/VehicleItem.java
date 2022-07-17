package entity;

import enums.ReservationStatus;
import enums.VehicleStatus;

import java.time.LocalDateTime;


public class VehicleItem {

    private String vehicleId;
    private String vehicleName;
    private Double hourlyRentalCost;
    private String vehicleType;
    private BranchItem branch;
    private ReservationStatus reservationStatus;
    private LocalDateTime reservationTime;
    private LocalDateTime vehicleAddedDate;
    private VehicleStatus vehicleStatus;

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public VehicleStatus getVehicleStatus() {
        return vehicleStatus;
    }

    public void setVehicleStatus(VehicleStatus vehicleStatus) {
        this.vehicleStatus = vehicleStatus;
    }

    public LocalDateTime getVehicleAddedDate() {
        return vehicleAddedDate;
    }

    public void setVehicleAddedDate(LocalDateTime vehicleAddedDate) {
        this.vehicleAddedDate = vehicleAddedDate;
    }

    public entity.BranchItem getBranch() {
        return branch;
    }

    public void setBranch(entity.BranchItem branch) {
        this.branch = branch;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Double getHourlyRentalCost() {
        return hourlyRentalCost;
    }

    public void setHourlyRentalCost(Double hourlyRentalCost) {
        this.hourlyRentalCost = hourlyRentalCost;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void bookVehicle(String branchId, String vehicleType, long start, long end) {


    }

    public void returnVehicle(String branchId,String vehicleType) {


    }
}
