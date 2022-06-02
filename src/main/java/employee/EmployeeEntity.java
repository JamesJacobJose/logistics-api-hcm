package employee;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.vertx.http.runtime.devmode.Json;

@Entity(name = "employees")
public class EmployeeEntity extends PanacheEntity{

    @ManyToOne
    @JoinColumn(name = "id")
    public Organization organization;

    @ManyToOne
    @JoinColumn(name = "id")
    public Department department;

    @OneToMany
    @JoinColumn(name = "id")
    public AssetEntity assetEntity;

    public String code;
    public String name;
    public String shortName;
    public String designation;
    public Json details;
    public String notes;
    public String createdBy;
    public String updatedBy;
    public Timestamp createdAt;
    public Timestamp updatedAt;

}
