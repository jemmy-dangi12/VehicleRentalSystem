package service.impl;

import entity.BranchItem;
import entity.Invoice;
import entity.VehicleItem;
import entity.VehicleReservationItem;
import enums.ReservationStatus;
import enums.ReservationType;
import exceptions.ReservationNotFoundException;
import exceptions.VehicleNotAddedException;
import exceptions.VehicleNotBookedException;
import repository.VehicleRepository;
import service.IVehicleInventory;
import service.InvoiceService;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static enums.VehicleStatus.AVAILABLE;

public class VehicleInventoryImpl implements IVehicleInventory {

    VehicleRepository vehicleRepository;

    InvoiceService invoiceService;


    public VehicleInventoryImpl(VehicleRepository vehicleRepository,InvoiceService invoiceService) {
        this.vehicleRepository = vehicleRepository;
        this.invoiceService = invoiceService;
    }

    @Override
    public List<String> getVehiclesByBranchAndTime(BranchItem branch, LocalDateTime startTime, LocalDateTime endTime) {
        return  VehicleRepository.vehicleItemMap.values().stream().filter(vehicle ->
                vehicle.getBranch().getBranchId().equalsIgnoreCase(branch.getBranchId())
                        && vehicle.getVehicleAddedDate()!= null
                        && (vehicle.getReservationStatus().equals(ReservationStatus.AVAILABLE) || endTime.isBefore(vehicle.getReservationTime())))
                .sorted(Comparator.comparing(VehicleItem::getHourlyRentalCost))
                .map(VehicleItem::getVehicleId)
                .collect(Collectors.toList());
    }

    @Override
    public void addVehicle(BranchItem branch, VehicleItem vehicleItem) throws VehicleNotAddedException {
        vehicleItem.setBranch(branch);
        vehicleItem.setVehicleId(vehicleItem.getVehicleId());
        vehicleItem.setVehicleAddedDate(LocalDateTime.now());
        vehicleItem.setVehicleStatus(AVAILABLE);
        vehicleItem.setReservationStatus(ReservationStatus.AVAILABLE);
        vehicleRepository.addVehicle(branch,vehicleItem);
    }

    @Override
    public synchronized VehicleReservationItem bookVehicle(BranchItem branch, VehicleItem vehicleItem, LocalDateTime startTime,
                                                           LocalDateTime endTime) {
        List<VehicleItem> vehicleItemList = getVehiclesByBranchAndVehicleName(branch,vehicleItem);
        if (null == vehicleItemList || vehicleItemList.isEmpty()) {
            throw new VehicleNotBookedException();
        }
        vehicleItem =  vehicleItemList.get(0);
        vehicleItem.setReservationStatus(ReservationStatus.BOOKED);
        vehicleItem.setReservationTime(startTime);
        VehicleReservationItem vehicleReservationItem = new VehicleReservationItem();
        vehicleReservationItem.setBranch(branch);
        vehicleReservationItem.setVehicleItem(vehicleItem);
        vehicleReservationItem.setReservationNo(UUID.randomUUID().toString());
        vehicleReservationItem.setReservationStatus(ReservationStatus.BOOKED);
        vehicleReservationItem.setBookingDate(LocalDateTime.now());
        vehicleReservationItem.setBookedFromDate(startTime);
        vehicleReservationItem.setBookedTillDate(endTime);
        vehicleReservationItem.setReservationType(ReservationType.HOUR);
        vehicleReservationItem.setReservationAmount(vehicleItem.getHourlyRentalCost());
        vehicleRepository.saveReservationDetails(vehicleReservationItem);
        vehicleRepository.updateVehicleReservationStatus(branch,vehicleItem,ReservationStatus.BOOKED);
        Invoice invoice = invoiceService.computeInvoice(vehicleReservationItem);
        vehicleReservationItem.setReservationAmount(invoice.getTotalAmount());
        return vehicleReservationItem;
    }

    public List<VehicleItem> getVehiclesByBranchAndVehicleName(BranchItem branch, VehicleItem vehicleItem) {
        List<VehicleItem> optionalVehicleItem = VehicleRepository.vehicleItemMap.values().stream().filter(vehicleItem1 -> vehicleItem1.getBranch() != null
                && vehicleItem1.getBranch().getBranchId().equalsIgnoreCase(branch.getBranchId())
                && vehicleItem1.getVehicleName().equalsIgnoreCase(vehicleItem.getVehicleName())
                && vehicleItem1.getVehicleStatus().equals(AVAILABLE)
                && vehicleItem1.getReservationStatus().equals(ReservationStatus.AVAILABLE))
                .sorted(Comparator.comparing(VehicleItem::getHourlyRentalCost))
                .collect(Collectors.toList());;
        return optionalVehicleItem;
    }

    @Override
    public void returnVehicle(BranchItem branch, VehicleItem vehicleItem) throws ReservationNotFoundException {
        VehicleReservationItem vehicleReservationItem = vehicleRepository.getVehicleReservation(branch.getBranchId(), vehicleItem.getVehicleId());
        if(null == vehicleReservationItem) {
            throw new ReservationNotFoundException("no reservation found in branch :"+ branch.getBranchId(), "for vehicle :" + vehicleItem.getVehicleId());
        }
        vehicleItem.setReservationStatus(ReservationStatus.AVAILABLE);
        vehicleRepository.updateVehicleReservationStatus(branch, vehicleItem,ReservationStatus.AVAILABLE);
    }
}
