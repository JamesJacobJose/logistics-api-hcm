package employee;

import java.sql.Timestamp;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "employees")
public class EmployeeEntity extends PanacheEntity{

    public Long organizationId;
    public Long departmentId;
    public String code;
    public String name;
    public String shortName;
    public String designation;
    public String details;
    public String notes;
    public String createdBy;
    public String updatedBy;
    public Timestamp createdAt;
    public Timestamp updatedAt;

}
