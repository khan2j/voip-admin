package com.voipadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.voipadmin.domain.Vendor} entity.
 */
public class VendorDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String name;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof VendorDTO)) {
            return false;
        }

        return id != null && id.equals(((VendorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "VendorDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
