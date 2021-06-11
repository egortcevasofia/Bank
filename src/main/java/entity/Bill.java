package entity;

import java.time.LocalDateTime;

public class Bill {
    private Long id;
    private LocalDateTime localDateTime;
    private Double payment;
    private Long clientId;

    public Bill(Long id, LocalDateTime localDateTime, Double payment, Long clientId) {
        this.id = id;
        this.localDateTime = localDateTime;
        this.payment = payment;
        this.clientId = clientId;
    }

    public Bill (LocalDateTime localDateTime, Double payment, Long clientId) {
        this.localDateTime = localDateTime;
        this.payment = payment;
        this.clientId = clientId;
    }

    public Bill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
}
