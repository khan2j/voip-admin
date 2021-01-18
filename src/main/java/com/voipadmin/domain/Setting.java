package com.voipadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Setting.
 */
@Entity
@Table(name = "setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Setting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "text_value")
    private String textValue;

    @ManyToOne
    @JsonIgnoreProperties(value = "settings", allowSetters = true)
    private Option option;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "setting_selected_values",
               joinColumns = @JoinColumn(name = "setting_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "selected_values_id", referencedColumnName = "id"))
    private Set<OptionValue> selectedValues = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "settings", allowSetters = true)
    private Device device;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextValue() {
        return textValue;
    }

    public Setting textValue(String textValue) {
        this.textValue = textValue;
        return this;
    }

    public void setTextValue(String textValue) {
        this.textValue = textValue;
    }

    public Option getOption() {
        return option;
    }

    public Setting option(Option option) {
        this.option = option;
        return this;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Set<OptionValue> getSelectedValues() {
        return selectedValues;
    }

    public Setting selectedValues(Set<OptionValue> optionValues) {
        this.selectedValues = optionValues;
        return this;
    }

    public Setting addSelectedValues(OptionValue optionValue) {
        this.selectedValues.add(optionValue);
        optionValue.getSettings().add(this);
        return this;
    }

    public Setting removeSelectedValues(OptionValue optionValue) {
        this.selectedValues.remove(optionValue);
        optionValue.getSettings().remove(this);
        return this;
    }

    public void setSelectedValues(Set<OptionValue> optionValues) {
        this.selectedValues = optionValues;
    }

    public Device getDevice() {
        return device;
    }

    public Setting device(Device device) {
        this.device = device;
        return this;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Setting)) {
            return false;
        }
        return id != null && id.equals(((Setting) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Setting{" +
            "id=" + getId() +
            ", textValue='" + getTextValue() + "'" +
            "}";
    }
}
