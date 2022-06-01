package employees;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;

import assets.AssetEntity;


public class EmployeeEntity {
	@OneToMany(targetEntity = AssetEntity.class, cascade = CascadeType.ALL, orphanRemoval = true)
    public Set<AssetEntity> assets;

}
