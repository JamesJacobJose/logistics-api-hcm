package assets;

import static javax.ws.rs.core.Response.Status.CONFLICT;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.quarkus.panache.common.Sort;
import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;

@Path("/assets")
@Tag(name = "Assets")
public class AssetResource {
	@Inject
	AssetTransformer assetTransformer;

	// Create Asset
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response createasset(Asset asset) {
		AssetEntity assetEntity = assetTransformer.toEntity(asset);
		assetEntity.persist();
		return Response.status(Status.CREATED).build();
	}

	// Get all assets
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listAllAssets() {
		return Response.ok(AssetEntity.listAll()).build();
	}

	// Get asset by ID
	@Path("/{id}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAssetById(@PathParam("id") Long id) {
		AssetEntity assetEntity = AssetEntity.findById(id);
		return Response.ok(assetTransformer.toResource(assetEntity)).build();
	}

	// Update asset
	@Path("/{id}")
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response updateAsset(@PathParam("id") Long id, Asset asset) {
		AssetEntity assetEntity = AssetEntity.findById(id);

		if (Objects.nonNull(asset.getOrganizationId())) {
			assetEntity.organizationId = asset.getOrganizationId();
		}
		if (Objects.nonNull(asset.getDetails())) {
			assetEntity.details = asset.getDetails();
		}
		if (Objects.nonNull(asset.getCode())) {
			assetEntity.code = asset.getCode();
		}
		if (Objects.nonNull(asset.getName())) {
			assetEntity.name = asset.getName();
		}

		if (Objects.nonNull(asset.getStatus())) {
			assetEntity.status = asset.getStatus();
		}
		if (Objects.nonNull(asset.getCreatedBy())) {
			assetEntity.createdBy = asset.getCreatedBy();
		}
		if (Objects.nonNull(asset.getUpdatedBy())) {
			assetEntity.updatedBy = asset.getUpdatedBy();
		}
		if (Objects.nonNull(asset.getCreatedAt())) {
			assetEntity.createdAt = asset.getCreatedAt();
		}
		if (Objects.nonNull(asset.getUpdatedAt())) {
			assetEntity.updatedAt = asset.getUpdatedAt();
		}
		return Response.noContent().build();
	}

	// Delete asset by ID
	@Path("/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional
	public Response deleteAsset(@PathParam("id") Long id) {
		AssetEntity.deleteById(id);
		return Response.noContent().build();
	}

}