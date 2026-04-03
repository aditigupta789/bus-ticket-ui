package com.cg.busticketui.dto.response;

/**
 * BookingResponseDto represents the response data for a booking entity.
 *
 * <p>
 * This DTO is used to transfer booking-related information from the backend
 * to the frontend layer. It encapsulates details such as booking ID,
 * trip ID, seat number, and booking status.
 * </p>
 *
 * <p>
 * Typically used in controller responses and service layers to ensure
 * separation between internal models and external API representations.
 * </p>
 */
public class BookingResponseDto {

    /**
     * Unique identifier for the booking.
     */
    private Integer bookingId;

    /**
     * Identifier of the associated trip.
     */
    private Integer tripId;

    /**
     * Seat number assigned to the booking.
     */
    private Integer seatNumber;

    /**
     * Current status of the booking (e.g., CONFIRMED, CANCELLED).
     */
    private String status;

    /**
     * Gets the booking ID.
     *
     * @return the booking ID
     */
    public Integer getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking ID.
     *
     * @param bookingId the booking ID to set
     */
    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Gets the trip ID.
     *
     * @return the trip ID
     */
    public Integer getTripId() {
        return tripId;
    }

    /**
     * Sets the trip ID.
     *
     * @param tripId the trip ID to set
     */
    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    /**
     * Gets the seat number.
     *
     * @return the seat number
     */
    public Integer getSeatNumber() {
        return seatNumber;
    }

    /**
     * Sets the seat number.
     *
     * @param seatNumber the seat number to set
     */
    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    /**
     * Gets the booking status.
     *
     * @return the booking status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the booking status.
     *
     * @param status the booking status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}