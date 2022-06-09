package assets;

import java.sql.Timestamp;

import javax.persistence.Column;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@RegisterForReflection
@Data
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})

public class Asset {
	public Long id;
	public String organizationId;
	public String employeeId;
	  @Type(type = "jsonb")
	  @Column(columnDefinition = "jsonb")
	  private Object details;
	public String code;
	public String name;
	public Boolean status;
	public Timestamp issueDate;
	public String createdBy;
	public String updatedBy;
	public Timestamp createdAt;
	public Timestamp updatedAt;
}
