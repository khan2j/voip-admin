package com.voipadmin.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link com.voipadmin.domain.ResponsiblePerson} entity.
 */
public class ResponsiblePersonDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String firstName;

    private String secondName;

    @NotNull
    private String lastName;

    private String position;

    private String room;


    private Long departmentId;

    private String departmentName;
    
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResponsiblePersonDTO)) {
            return false;
        }

        return id != null && id.equals(((ResponsiblePersonDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ResponsiblePersonDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", firstName='" + getFirstName() + "'" +
            ", secondName='" + getSecondName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", position='" + getPosition() + "'" +
            ", room='" + getRoom() + "'" +
            ", departmentId=" + getDepartmentId() +
            ", departmentName='" + getDepartmentName() + "'" +
            "}";
    }
}
