package assets;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;

import employees.EmployeeEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import io.smallrye.mutiny.Uni;
@Entity(name = "assets")
@Cacheable
public class AssetEntity extends PanacheEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "assets_sequence")
	@SequenceGenerator(name = "assets_sequence", sequenceName = "assets_sequence")
	public Long id;

	public String organization;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	public EmployeeEntity employee;

	public String code;
	public String name;
	public Date issueDate;
	public Boolean status;
	public String createdBy;
	public String updatedBy;
	public Timestamp createdAt;
	public Timestamp updatedAt;
}
