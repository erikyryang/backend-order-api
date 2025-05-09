package com.marketplace.backend.domain.user.waiter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.marketplace.backend.domain.user.AbstractUserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "waiter_tbl")
public class WaiterEntity extends AbstractUserEntity {

    private String employeeId;
}
