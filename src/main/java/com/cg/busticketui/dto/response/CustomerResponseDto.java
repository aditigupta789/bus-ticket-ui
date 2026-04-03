package com.cg.busticketui.dto.response;

/**
 * CustomerResponseDto represents the response data for a customer entity.
 *
 * <p>
 * This DTO is used to transfer customer-related information from the backend
 * to the frontend layer. It contains essential details such as customer ID,
 * name, contact information, and location.
 * </p>
 *
 * <p>
 * It helps in maintaining a clear separation between internal domain models
 * and external API responses.
 * </p>
 */
public class CustomerResponseDto {

    /**
     * Unique identifier for the customer.
     */
    private Integer customerId;

    /**
     * Name of the customer.
     */
    private String name;

    /**
     * Email address of the customer.
     */
    private String email;

    /**
     * Contact phone number of the customer.
     */
    private String phone;

    /**
     * City where the customer resides.
     */
    private String city;

    /**
     * Gets the customer ID.
     *
     * @return the customer ID
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID.
     *
     * @param customerId the customer ID to set
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the customer name.
     *
     * @return the customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the customer name.
     *
     * @param name the customer name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the customer email.
     *
     * @return the customer email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer email.
     *
     * @param email the customer email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the customer phone number.
     *
     * @return the customer phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the customer phone number.
     *
     * @param phone the customer phone number to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the customer's city.
     *
     * @return the customer's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the customer's city.
     *
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }
}