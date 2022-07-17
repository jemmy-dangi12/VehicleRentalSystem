package service;

import entity.BranchItem;
import exceptions.BranchAlreadyExistsException;

import java.util.Set;

public interface IVehicleRentalService {

    public void addBranch(BranchItem branchItem, Set<String> vehicleSet) throws BranchAlreadyExistsException;
}
