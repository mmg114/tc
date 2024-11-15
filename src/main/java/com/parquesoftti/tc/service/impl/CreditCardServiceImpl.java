package com.parquesoftti.tc.service.impl;

import com.parquesoftti.tc.model.CreditCard;
import com.parquesoftti.tc.repository.CreditCardRepository;
import com.parquesoftti.tc.service.CreditCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CreditCard> getCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditCard> getCardById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo y no nulo.");
        }
        return creditCardRepository.findById(id);
    }
    @Transactional(readOnly = false, rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public CreditCard saveCreditCard(CreditCard creditCard) {
        validateCreditCard(creditCard);
        return creditCardRepository.save(creditCard);
    }
    @Transactional( rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public CreditCard updateCreditCard(CreditCard creditCard, Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo y no nulo.");
        }

        Optional<CreditCard> existingCard = getCardById(id);
        if (existingCard.isEmpty()) {
            throw new RuntimeException("No se encontró la tarjeta de crédito en la base de datos.");
        }

        creditCard.setId(id);
        validateCreditCard(creditCard);
        return creditCardRepository.save(creditCard);
    }
    @Transactional(readOnly = false, rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    @Override
    public void deleteCard(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("El ID debe ser positivo y no nulo.");
        }

        Optional<CreditCard> existingCard = getCardById(id);
        if (existingCard.isEmpty()) {
            throw new RuntimeException("La tarjeta de crédito no existe y no se puede eliminar.");
        }
        creditCardRepository.deleteById(id);
    }

    /**
     * Valida los datos básicos de una tarjeta de crédito.
     *
     * @param creditCard La tarjeta de crédito a validar
     */
    private void validateCreditCard(CreditCard creditCard) {
        if (creditCard == null) {
            throw new IllegalArgumentException("La tarjeta de crédito no debe ser nula.");
        }

        if (creditCard.getCardNumber() == null || creditCard.getCardNumber().isEmpty()) {
            throw new IllegalArgumentException("El número de tarjeta no debe estar vacío.");
        }

        if (creditCard.getCardNumber().length() != 16 ) {
            throw new IllegalArgumentException("El número de tarjeta debe tener 16 dígitos numéricos.");
        }

        if (creditCard.getExpirationDate() == null) {
            throw new IllegalArgumentException("La fecha de vencimiento no debe ser nula.");
        }

        if (creditCard.getCvv() == null) {
            throw new IllegalArgumentException("El CVV no debe ser nula.");
        }

    }
}