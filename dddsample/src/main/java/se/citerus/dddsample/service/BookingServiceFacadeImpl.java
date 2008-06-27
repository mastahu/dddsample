package se.citerus.dddsample.service;

import se.citerus.dddsample.domain.*;
import se.citerus.dddsample.repository.CargoRepository;
import se.citerus.dddsample.repository.CarrierMovementRepository;
import se.citerus.dddsample.repository.LocationRepository;
import se.citerus.dddsample.service.dto.CargoRoutingDTO;
import se.citerus.dddsample.service.dto.ItineraryCandidateDTO;
import se.citerus.dddsample.service.dto.LocationDTO;
import se.citerus.dddsample.service.dto.assembler.CargoRoutingDTOAssembler;
import se.citerus.dddsample.service.dto.assembler.ItineraryCandidateDTOAssembler;
import se.citerus.dddsample.service.dto.assembler.LocationDTOAssembler;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


/**
 * This implementation has additional support from the infrastructure, for exposing as an RMI
 * service and for keeping the OR-mapper unit-of-work open during DTO assembly,
 * analogous to the view rendering for web interfaces.
 *
 * See context-remote.xml.  
 */
public class BookingServiceFacadeImpl implements BookingServiceFacade {

  private BookingService bookingService;
  private LocationRepository locationRepository;
  private CargoRepository cargoRepository;
  private CarrierMovementRepository carrierMovementRepository;

  public List<LocationDTO> listShippingLocations() {
    final List<Location> allLocations = locationRepository.findAll();
    final LocationDTOAssembler assembler = new LocationDTOAssembler();
    return assembler.toDTOList(allLocations);
  }

  public String registerNewCargo(String origin, String destination) {
    TrackingId trackingId = bookingService.bookNewCargo(new UnLocode(origin), new UnLocode(destination));
    return trackingId.idString();
  }

  public CargoRoutingDTO loadCargoForRouting(String trackingId) {
    final Cargo cargo = bookingService.loadCargoForRouting(new TrackingId(trackingId));
    final CargoRoutingDTOAssembler assembler = new CargoRoutingDTOAssembler();
    return assembler.toDTO(cargo);
  }

  public void assignCargoToRoute(String trackingId, ItineraryCandidateDTO itineraryCandidateDTO) {
    final Itinerary itinerary = new ItineraryCandidateDTOAssembler().fromDTO(itineraryCandidateDTO, carrierMovementRepository, locationRepository);
    bookingService.assignCargoToRoute(new TrackingId(trackingId), itinerary);
  }

  public List<CargoRoutingDTO> listAllCargos() {
    final List<Cargo> cargoList = cargoRepository.findAll();
    final List<CargoRoutingDTO> dtoList = new ArrayList<CargoRoutingDTO>(cargoList.size());
    final CargoRoutingDTOAssembler assembler = new CargoRoutingDTOAssembler();
    for (Cargo cargo : cargoList) {
      dtoList.add(assembler.toDTO(cargo));
    }
    return dtoList;
  }

  public List<ItineraryCandidateDTO> requestPossibleRoutesForCargo(String trackingId) throws RemoteException {
    final List<Itinerary> itineraries = bookingService.requestPossibleRoutesForCargo(new TrackingId(trackingId));

    final List<ItineraryCandidateDTO> itineraryCandidates = new ArrayList<ItineraryCandidateDTO>(itineraries.size());
    final ItineraryCandidateDTOAssembler dtoAssembler = new ItineraryCandidateDTOAssembler();
    for (Itinerary itinerary : itineraries) {
      itineraryCandidates.add(dtoAssembler.toDTO(itinerary));
    }

    return itineraryCandidates;
  }

  public void setBookingService(BookingService bookingService) {
    this.bookingService = bookingService;
  }

  public void setLocationRepository(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }

  public void setCargoRepository(CargoRepository cargoRepository) {
    this.cargoRepository = cargoRepository;
  }

  public void setCarrierMovementRepository(CarrierMovementRepository carrierMovementRepository) {
    this.carrierMovementRepository = carrierMovementRepository;
  }
}
