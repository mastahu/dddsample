package se.citerus.dddsample.tracking.booking.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DTO for presenting and selecting an itinerary from a collection of candidates.
 */
public final class RouteCandidateDTO implements Serializable {

  private final List<LegDTO> legs;

  /**
   * Constructor.
   *
   * @param legs The legs for this itinerary.
   */
  public RouteCandidateDTO(final List<LegDTO> legs) {
    this.legs = new ArrayList<LegDTO>(legs);
  }

  /**
   * @return An unmodifiable list DTOs.
   */
  public List<LegDTO> getLegs() {
    return Collections.unmodifiableList(legs);
  }
}