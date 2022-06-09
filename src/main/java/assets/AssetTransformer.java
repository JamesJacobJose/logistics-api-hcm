package assets;

import java.util.List;
import org.mapstruct.Mapper;
import util.QuarkusMappingConfig;

@Mapper(config = QuarkusMappingConfig.class)

public interface AssetTransformer {
	Asset toResource(AssetEntity assetEntity);

    List<Asset> map(List<AssetEntity> assetEntityList);

    AssetEntity toEntity(Asset asset);

}
