package assets;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
@Mapper(componentModel = "cdi")
public interface AssetMapper {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

	@Named("tsToStr")
	public static String tsToStr(Timestamp ts) {
    	return ts.toInstant().toString();
	}

	@Named("strToTs")
	public static Timestamp strToTs(String str) {
    	try {
      		return Timestamp.from(Instant.parse(str));
    	} catch (Exception e) {
      		return null;
    	}
  	}

	@Mapping(source = "createdAt", target = "createdAt", qualifiedByName = "tsToStr")
	@Mapping(source = "updatedAt", target = "updatedAt", qualifiedByName = "tsToStr")
	@Mapping(source = "employee.id", target = "employeeId")
	Asset toDomain(AssetEntity entity);

	@Mapping(source = "employeeId", target = "employee.id")
	AssetEntity toEntity(AssetRequest request);

	default List<Asset> toDomainList(List<AssetEntity> list) {
		return list.stream().map(this::toDomain).collect(Collectors.toList());
	}
	default List<AssetEntity> toEntityList(List<AssetRequest> list) {
		return list.stream().map(this::toEntity).collect(Collectors.toList());
	}
}
