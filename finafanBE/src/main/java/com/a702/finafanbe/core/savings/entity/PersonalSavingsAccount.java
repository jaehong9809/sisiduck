package com.a702.finafanbe.core.savings.entity;

import com.a702.finafanbe.core.savings.dto.CreateSavingAccountRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@DiscriminatorValue("personal")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PersonalSavingsAccount extends SavingsAccount {

    private Long entertainerId;

    private LocalDateTime lastTransactionDate;

}
