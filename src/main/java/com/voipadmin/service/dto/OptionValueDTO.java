package com.voipadmin.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.voipadmin.domain.OptionValue} entity.
 */
public class OptionValueDTO implements Serializable {
    
    private Long id;

    private String value;


    private Long optionId;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionId) {
        this.optionId = optionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionValueDTO)) {
            return false;
        }

        return id != null && id.equals(((OptionValueDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionValueDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", optionId=" + getOptionId() +
            "}";
    }
}
