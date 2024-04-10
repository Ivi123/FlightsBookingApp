package main.mapper;

import main.dto.PaymentDetailsDTO;
import main.model.PaymentDetails;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsMapper {
    public PaymentDetails toEntity(PaymentDetailsDTO dto) {
        PaymentDetails entity = new PaymentDetails();
        entity.setCardNumber(dto.getCardNumber());
        entity.setCardHolderName(dto.getCardHolderName());
        entity.setExpirationMonth(dto.getExpirationMonth());
        entity.setExpirationYear(dto.getExpirationYear());
        entity.setCvv(dto.getCvv());
        entity.setCurrency(dto.getCurrency());
        entity.setStatus(dto.getStatus());
        return entity;
    }

    public PaymentDetailsDTO toDTO(PaymentDetails entity) {
        PaymentDetailsDTO dto = new PaymentDetailsDTO();
        dto.setCardNumber(entity.getCardNumber());
        dto.setCardHolderName(entity.getCardHolderName());
        dto.setExpirationMonth(entity.getExpirationMonth());
        dto.setExpirationYear(entity.getExpirationYear());
        dto.setCvv(entity.getCvv());
        dto.setCurrency(entity.getCurrency());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
