package employee;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.vertx.http.runtime.devmode.Json;

@Entity(name = "employees")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
public class EmployeeEntity extends PanacheEntity{

    @ManyToOne
    @JoinColumn(name = "id" ,insertable=false, updatable=false)
    public OrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "id" ,insertable=false, updatable=false)
    public DepartmentEntity department;

    @OneToMany
    @JoinColumn(name = "assetId" ,insertable=false, updatable=false)
    public AssetEntity assetEntity;

    public String code;

    public String name;

    public String shortName;

    public String designation;

    @Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
    public Object details;
    
    public String notes;

    public String createdBy;

    public String updatedBy;

    public Timestamp createdAt;
    
    public Timestamp updatedAt;

}
