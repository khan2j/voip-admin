package com.voipadmin.service.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import com.voipadmin.domain.enumeration.OptionValueType;

/**
 * A DTO for the {@link com.voipadmin.domain.Option} entity.
 */
public class OptionDTO implements Serializable {

    private Long id;

    private String code;

    private String descr;

    private OptionValueType valueType;

    private Boolean multiple;

    private Set<OptionValueDTO> possibleValues;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public OptionValueType getValueType() {
        return valueType;
    }

    public void setValueType(OptionValueType valueType) {
        this.valueType = valueType;
    }

    public Boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(Boolean multiple) {
        this.multiple = multiple;
    }

    public Set<OptionValueDTO> getPossibleValues() {
        return possibleValues;
    }

    public void setPossibleValues(Set<OptionValueDTO> possibleValues) {
        this.possibleValues = possibleValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionDTO)) {
            return false;
        }

        return id != null && id.equals(((OptionDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", descr='" + getDescr() + "'" +
            ", valueType='" + getValueType() + "'" +
            ", multiple='" + isMultiple() + "'" +
            "}";
    }
}
