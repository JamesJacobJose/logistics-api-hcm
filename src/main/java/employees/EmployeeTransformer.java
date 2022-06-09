package employees;

import java.util.List;


import org.mapstruct.Mapper;

import util.QuarkusMappingConfig;


@Mapper(config = QuarkusMappingConfig.class)
public interface EmployeeTransformer {
	Employee toResource(EmployeeEntity employeeEntity);

    List<Employee> map(List<EmployeeEntity> employeeEntityList);

    EmployeeEntity toEntity(Employee employee);


}
