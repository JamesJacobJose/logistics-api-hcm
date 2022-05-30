package assets;

import java.sql.Timestamp;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;

@RegisterForReflection
@Data
public class Asset {
	private Long id;

	private String organizationId;
	private String employeeId;
	private String code;
	private String name;
	private String status;
	private Timestamp issueDate;
	private String createdBy;
	private String updatedBy;
	private Timestamp createdAt;
	private Timestamp updatedAt;
}
