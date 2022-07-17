package entity;

import enums.ReservationStatus;
import enums.ReservationType;

import java.time.LocalDateTime;

public class VehicleReservationItem {
    private String reservationNo;
    private LocalDateTime bookingDate;
    private LocalDateTime bookedFromDate;
    private LocalDateTime bookedTillDate;
    private LocalDateTime returnDate;
    private ReservationStatus reservationStatus;
    private VehicleItem vehicleItem;
    private BranchItem branch;
    private Double reservationAmount;
    private ReservationType reservationType;

    public String getReservationNo() {
        return reservationNo;
    }

    public void setReservationNo(String reservationNo) {
        this.reservationNo = reservationNo;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public LocalDateTime getBookedFromDate() {
        return bookedFromDate;
    }

    public void setBookedFromDate(LocalDateTime bookedFromDate) {
        this.bookedFromDate = bookedFromDate;
    }

    public LocalDateTime getBookedTillDate() {
        return bookedTillDate;
    }

    public void setBookedTillDate(LocalDateTime bookedTillDate) {
        this.bookedTillDate = bookedTillDate;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime returnDate) {
        this.returnDate = returnDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public VehicleItem getVehicleItem() {
        return vehicleItem;
    }

    public void setVehicleItem(VehicleItem vehicleItem) {
        this.vehicleItem = vehicleItem;
    }

    public BranchItem getBranch() {
        return branch;
    }

    public void setBranch(BranchItem branch) {
        this.branch = branch;
    }

    public Double getReservationAmount() {
        return reservationAmount;
    }

    public void setReservationAmount(Double reservationAmount) {
        this.reservationAmount = reservationAmount;
    }

    public ReservationType getReservationType() {
        return reservationType;
    }

    public void setReservationType(ReservationType reservationType) {
        this.reservationType = reservationType;
    }
}
