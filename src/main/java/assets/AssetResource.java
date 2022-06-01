package assets;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
@Path("/assets")
@Tag(name = "Assets")
public class AssetResource {
	private static final Logger LOGGER = Logger.getLogger(AssetResource.class.getName());

//	@Inject
//	AssetMapper mapper;
	@GET
	public Uni<List<Asset>> get() {
		return AssetEntity.<AssetEntity>listAll(Sort.by("name")).map(mapper::toDomainList);
	}

	@GET
	@Path("/{id}")
	public Uni<Asset> getSingle(Long id) {
		return AssetEntity.<AssetEntity>findById(id).onItem().transform(mapper::toDomain);
	}

	@GET
	@Path("/by-employee-id/{employeeId}")
	public Uni<List<Asset>> getByEmployeeId(Long employeeId) {
		return AssetEntity.findByEmployee(employeeId).map(mapper::toDomainList);
	}

	@POST
	public Uni<Response> create(AssetRequest request) {
		if (request == null) {
			throw new WebApplicationException("Id was invalidly set on request.", 422);
		}

		AssetEntity entity = mapper.toEntity(request);
		return Panache.<AssetEntity>withTransaction(entity::persistAndFlush).onItem()
				.transform(inserted -> Response.ok(inserted).status(CREATED).build())
				.onFailure(PersistenceException.class)
				.recoverWithItem(e -> e.getMessage().contains("23505") ? Response.ok().status(CONFLICT).build()
						: Response.ok(e.getMessage()).status(INTERNAL_SERVER_ERROR).build());
	}

	@PUT
	@Path("/{id}")
	public Uni<Response> update(Long id, AssetRequest assetrequest) {
		if (assetrequest == null) {
			throw new WebApplicationException("Name was not set on request.", 422);
		}

		AssetEntity request = mapper.toEntity(assetrequest);
		return Panache
				.withTransaction(() -> AssetEntity.<AssetEntity>findById(id).onItem().ifNotNull().invoke(entity -> {
					entity.organization = request.organization;
					entity.employee = request.employee;
					entity.code = request.code;
					entity.name = request.name;
					entity.issueDate = request.issueDate;
					entity.status = request.status;
				})).onItem().ifNotNull().transform(updated -> Response.ok(mapper.toDomain(updated)).build()).onItem()
				.ifNull().continueWith(Response.ok().status(NOT_FOUND)::build);
	}

	@DELETE
	@Path("/{id}")
	public Uni<Response> delete(Long id) {
		return Panache.withTransaction(() -> AssetEntity.deleteById(id))
				.map(deleted -> deleted ? Response.ok().status(NO_CONTENT).build()
						: Response.ok().status(NOT_FOUND).build());
	}

	@Provider
	public static class ErrorMapper implements ExceptionMapper<Exception> {

		@Inject
		ObjectMapper objectMapper;

		@Override
		public Response toResponse(Exception exception) {
			LOGGER.error("Failed to handle request", exception);

			Throwable throwable = exception;

			int code = 500;
			if (throwable instanceof WebApplicationException) {
				code = ((WebApplicationException) exception).getResponse().getStatus();
			}

			// This is a Mutiny exception and it happens, for example, when we try to insert
			// a new
			// code but the name is already in the database
			if (throwable instanceof CompositeException) {
				throwable = ((CompositeException) throwable).getCause();
			}

			ObjectNode exceptionJson = objectMapper.createObjectNode();
			exceptionJson.put("exceptionType", throwable.getClass().getName());
			exceptionJson.put("code", code);

			if (exception.getMessage() != null) {
				exceptionJson.put("error", throwable.getMessage());
			}

			return Response.status(code).entity(exceptionJson).build();
		}

	}

}