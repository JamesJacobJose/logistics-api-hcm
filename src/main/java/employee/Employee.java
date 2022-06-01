package employee;

import java.sql.Timestamp;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@RegisterForReflection
@Data
public class Employee {

    private Long id;
    private Long organizationId;
    private Long departmentId;
    private String code;
    private String name;
    private String shortName;
    private String designation;
    private String details;
    private String notes;
    private String createdBy;
    private String updatedBy;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    
}
