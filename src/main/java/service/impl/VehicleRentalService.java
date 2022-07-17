package service.impl;

import entity.BranchItem;
import exceptions.BranchAlreadyExistsException;
import repository.VehicleRepository;
import service.IVehicleRentalService;

import java.util.Set;


public class VehicleRentalService implements IVehicleRentalService {

    public void addBranch(BranchItem branchItem, Set<String> vehicleSet) throws BranchAlreadyExistsException {
        if(VehicleRepository.branchVehicleList.containsKey(branchItem.getBranchId())) {
            throw new BranchAlreadyExistsException();
        }
        VehicleRepository.branchVehicleList.put(branchItem.getBranchId(),vehicleSet);
    }
}
