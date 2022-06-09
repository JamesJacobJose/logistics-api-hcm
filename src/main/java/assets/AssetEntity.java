package assets;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import employees.EmployeeEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "assets")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
public class AssetEntity extends PanacheEntity {
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	public Object details;
	
//	@ManyToOne(fetch = FetchType.LAZY)
////	@JoinColumn(name = "assets_id")
//	private EmployeeEntity employees;
	
	public String organizationId;
	@ManyToOne
	@JoinColumn(name = "employeeId",insertable=false, updatable=false)
	public EmployeeEntity employees;
	public Long employeeId;

	public String code;
	public String name;
	public Date issueDate;
	public Boolean status;
	public String createdBy;
	public String updatedBy;
	public Timestamp createdAt;
	public Timestamp updatedAt;
}
