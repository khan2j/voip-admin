package com.voipadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A OptionValue.
 */
@Entity
@Table(name = "option_value")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class OptionValue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JsonIgnoreProperties(value = "possibleValues", allowSetters = true)
    private Option option;

    @ManyToMany(mappedBy = "selectedValues")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Setting> settings = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public OptionValue value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Option getOption() {
        return option;
    }

    public OptionValue option(Option option) {
        this.option = option;
        return this;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public Set<Setting> getSettings() {
        return settings;
    }

    public OptionValue settings(Set<Setting> settings) {
        this.settings = settings;
        return this;
    }

    public OptionValue addSettings(Setting setting) {
        this.settings.add(setting);
        setting.getSelectedValues().add(this);
        return this;
    }

    public OptionValue removeSettings(Setting setting) {
        this.settings.remove(setting);
        setting.getSelectedValues().remove(this);
        return this;
    }

    public void setSettings(Set<Setting> settings) {
        this.settings = settings;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OptionValue)) {
            return false;
        }
        return id != null && id.equals(((OptionValue) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OptionValue{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
