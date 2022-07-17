package repository;

import entity.BranchItem;
import entity.VehicleItem;
import entity.VehicleReservationItem;
import enums.ReservationStatus;
import exceptions.VehicleNotAddedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class VehicleRepository {
    public static Map<String,VehicleItem> vehicleItemMap = new ConcurrentHashMap<>();
    public static List<VehicleReservationItem> vehicleReservations = new ArrayList<>();
    public static Map<String, Set<String>> branchVehicleList = new ConcurrentHashMap<>();

    public void saveReservationDetails(VehicleReservationItem vehicleReservationItem) {
        vehicleReservations.add(vehicleReservationItem);
    }


    public void addVehicle(BranchItem branchItem, VehicleItem vehicleItemEntity) {
        if(!branchVehicleList.containsKey(branchItem.getBranchId())
                || branchVehicleList.get(branchItem.getBranchId()).isEmpty()
                || !branchVehicleList.get(branchItem.getBranchId()).contains(vehicleItemEntity.getVehicleName().toUpperCase())
                || (!vehicleItemMap.isEmpty() && vehicleItemMap.containsKey(vehicleItemEntity.getVehicleId()))) {
            throw new VehicleNotAddedException();
        }
        vehicleItemMap.put(vehicleItemEntity.getVehicleId(),vehicleItemEntity);
    }

    public VehicleReservationItem getVehicleReservation(String branchId, String vehicleId) {
        for(VehicleReservationItem vehicleReservationItem : vehicleReservations){
            if(vehicleReservationItem.getBranch() != null
                    && vehicleReservationItem.getBranch().getBranchId().equals(branchId)
                    && vehicleReservationItem.getVehicleItem() != null
                    && vehicleReservationItem.getVehicleItem().getVehicleId().equals(vehicleId)) {
                return vehicleReservationItem;
            }
        }
        return null;
    }

    public void updateVehicleReservationStatus(BranchItem branch, VehicleItem vehicleItem, ReservationStatus status) {
        for(VehicleItem vehicleItemEntity : vehicleItemMap.values()) {
            if(vehicleItemEntity.getVehicleId().equalsIgnoreCase(vehicleItem.getVehicleId())
            && vehicleItemEntity.getBranch().getBranchId().equalsIgnoreCase(branch.getBranchId())) {
                vehicleItemEntity.setReservationStatus(status);
                break;
            }
        }
    }
}
