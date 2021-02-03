package com.voipadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Vendor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "vendor")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<DeviceModel> deviceModels = new HashSet<>();

    @ManyToMany(mappedBy = "vendors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Option> options = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Vendor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<DeviceModel> getDeviceModels() {
        return deviceModels;
    }

    public Vendor deviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
        return this;
    }

    public Vendor addDeviceModels(DeviceModel deviceModel) {
        this.deviceModels.add(deviceModel);
        deviceModel.setVendor(this);
        return this;
    }

    public Vendor removeDeviceModels(DeviceModel deviceModel) {
        this.deviceModels.remove(deviceModel);
        deviceModel.setVendor(null);
        return this;
    }

    public void setDeviceModels(Set<DeviceModel> deviceModels) {
        this.deviceModels = deviceModels;
    }

    public Set<Option> getOptions() {
        return options;
    }

    public Vendor options(Set<Option> options) {
        this.options = options;
        return this;
    }

    public Vendor addOptions(Option option) {
        this.options.add(option);
        option.getVendors().add(this);
        return this;
    }

    public Vendor removeOptions(Option option) {
        this.options.remove(option);
        option.getVendors().remove(this);
        return this;
    }

    public void setOptions(Set<Option> options) {
        this.options = options;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Vendor)) {
            return false;
        }
        return id != null && id.equals(((Vendor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Vendor{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
