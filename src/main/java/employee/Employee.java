package employee;

import java.sql.Timestamp;

import javax.persistence.Column;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@RegisterForReflection
@Data
@TypeDefs({
    @TypeDef(name = "json", typeClass = JsonStringType.class),
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class Employee {

    private Long id;
    private Long organizationId;
    private Long departmentId;
    private Long assetId;
    private String code;
    private String name;
    private String shortName;
    private String designation;

    @Type(type = "jsonb")
	@Column(columnDefinition = "jsonb")
    private Object details;
    
    private String notes;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
}
