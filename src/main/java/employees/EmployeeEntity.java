package employees;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import assets.AssetEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name = "employees")
@Table(name = "employees")
@TypeDefs({ @TypeDef(name = "json", typeClass = JsonStringType.class),
		@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) })
public class EmployeeEntity extends PanacheEntity {
//	@OneToMany(mappedBy = "employees", cascade = CascadeType.ALL, orphanRemoval = true)
//	public Set<AssetEntity> assetEntity;
	@MapKey(name = "id")
	@OneToMany(mappedBy = "employees")
	protected Map<Long, AssetEntity> assets = new HashMap<>();
	
	public String organization;
	@Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
	public Object details;
	public String code;
	public String name;
	public String shortName;
	public String designation;
	public String notes;
	public Boolean status;
	public String createdBy;
	public String updatedBy;
	public Timestamp createdAt;
	public Timestamp updatedAt;

}
