package com.voipadmin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A AsteriskAccount.
 */
@Entity
@Table(name = "asterisk_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AsteriskAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "asterisk_id")
    private String asteriskId;

    @OneToOne(mappedBy = "asteriskAccount")
    @JsonIgnore
    private VoipAccount voipAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public AsteriskAccount username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAsteriskId() {
        return asteriskId;
    }

    public AsteriskAccount asteriskId(String asteriskId) {
        this.asteriskId = asteriskId;
        return this;
    }

    public void setAsteriskId(String asteriskId) {
        this.asteriskId = asteriskId;
    }

    public VoipAccount getVoipAccount() {
        return voipAccount;
    }

    public AsteriskAccount voipAccount(VoipAccount voipAccount) {
        this.voipAccount = voipAccount;
        return this;
    }

    public void setVoipAccount(VoipAccount voipAccount) {
        this.voipAccount = voipAccount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AsteriskAccount)) {
            return false;
        }
        return id != null && id.equals(((AsteriskAccount) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AsteriskAccount{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", asteriskId='" + getAsteriskId() + "'" +
            "}";
    }
}
