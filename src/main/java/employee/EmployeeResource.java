package employee;

import java.util.Objects;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/employees")
@Tag(name = "Employees")
public class EmployeeResource {

    @Inject
    EmployeeTransformer employeeTransformer ;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createEmployee(Employee employee) 
    {
        EmployeeEntity employeeEntity = employeeTransformer.toEntity(employee);
        employeeEntity.persist();
        return Response.status(Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllEmployees()
    {
        return Response.ok(EmployeeEntity.listAll()).build();
    }

    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") Long id) 
    {
        EmployeeEntity employeeEntity = EmployeeEntity.findById(id);
        return Response.ok(employeeTransformer.toResource(employeeEntity)).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response updateEmployee(@PathParam("id") Long id, Employee employee) 
    {
        EmployeeEntity employeeEntity = EmployeeEntity.findById(id);

        if (Objects.nonNull(employee.getOrganizationId())) {
            employeeEntity.organizationId = employee.getOrganizationId();
        }
        if (Objects.nonNull(employee.getDepartmentId())) {
            employeeEntity.departmentId = employee.getDepartmentId();
        }
        if (Objects.nonNull(employee.getAssetId())) {
            employeeEntity.assetId = employee.getAssetId();
        }
        if (Objects.nonNull(employee.getCode())) {
            employeeEntity.code = employee.getCode();
        }
        if (Objects.nonNull(employee.getName())) {
            employeeEntity.name = employee.getName();
        }
        if (Objects.nonNull(employee.getShortName())) {
            employeeEntity.shortName = employee.getShortName();
        }
        if (Objects.nonNull(employee.getDesignation())) {
            employeeEntity.designation = employee.getDesignation();
        }
        if (Objects.nonNull(employee.getDetails())) {
            employeeEntity.details = employee.getDetails();
        }
        if (Objects.nonNull(employee.getNotes())) {
            employeeEntity.notes = employee.getNotes();
        }
        if (Objects.nonNull(employee.getCreatedBy())) {
            employeeEntity.createdBy = employee.getCreatedBy();
        }
        if (Objects.nonNull(employee.getUpdatedBy())) {
            employeeEntity.updatedBy = employee.getUpdatedBy();
        }
        if (Objects.nonNull(employee.getCreatedAt())) {
            employeeEntity.createdAt = employee.getCreatedAt();
        }
        if (Objects.nonNull(employee.getUpdatedAt())) {
            employeeEntity.updatedAt = employee.getUpdatedAt();
        }
        return Response.noContent().build();
    }

    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteEmployee(@PathParam("id") Long id) 
    {
        EmployeeEntity.deleteById(id);
        return Response.noContent().build();
    }
    
}
