package entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BranchItem {

    private String branchId;
    private String branchName;
    private Location location;
    private List<VehicleReservationItem> reservationItemList;
    private Set<VehicleItem> vehicleItemList;


    public BranchItem() {
        this.reservationItemList = new ArrayList<>();
        this. vehicleItemList = new HashSet<>();
    }

    public BranchItem(String branchId,String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
    }

    public Set<VehicleItem> getVehicleItemList() {
        return vehicleItemList;
    }

    public void setVehicleItemList(Set<VehicleItem> vehicleItemList) {
        this.vehicleItemList = vehicleItemList;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<VehicleReservationItem> getReservationItemList() {
        return reservationItemList;
    }

    public void setReservationItemList(List<VehicleReservationItem> reservationItemList) {
        this.reservationItemList = reservationItemList;
    }
}
