package se.citerus.dddsample.service;

import se.citerus.dddsample.domain.Cargo;
import se.citerus.dddsample.domain.Itinerary;
import se.citerus.dddsample.domain.TrackingId;
import se.citerus.dddsample.domain.UnLocode;

import java.util.List;

/**
 * Cargo booking service.
 */
public interface BookingService {

  /**
   * Registers a new cargo in the tracking system, not yet routed.
   *
   * @param origin      cargo origin
   * @param destination cargo destination
   * @return Cargo tracking id
   */
  TrackingId bookNewCargo(UnLocode origin, UnLocode destination);

  /**
   * Loads a cargo for routing operations.
   *
   * @param trackingId cargo tracking id
   * @return A cargo with it's itinerary, or null if none found.
   */
  Cargo loadCargoForRouting(TrackingId trackingId);

  /**
   * Requests a list of itineraries describing possible routes for this cargo.
   * 
   * @param trackingId cargo tracking id
   * @return A list of possible itineraries for this cargo 
   */
  List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId);

  /**
   * Assigns a cargo to route.
   *
   * @param trackingId cargo tracking id
   * @param itinerary  the new itinerary describing the route
   */
  void assignCargoToRoute(TrackingId trackingId, Itinerary itinerary);

}
