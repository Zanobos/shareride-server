package it.zano.shareride.persistence.entities;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import it.zano.shareride.persistence.PersistenceController;

@Entity
@Table(name = PersistenceController.BOUNDING_BOXES)
public class BoundingBoxEntity extends BaseEntity {

	@OneToOne(cascade = CascadeType.ALL)
	private GeoPointEntity minimum;
	@OneToOne(cascade = CascadeType.ALL)
	private GeoPointEntity maximum;

	public GeoPointEntity getMinimum() {
		return minimum;
	}

	public void setMinimum(GeoPointEntity minimum) {
		this.minimum = minimum;
	}

	public GeoPointEntity getMaximum() {
		return maximum;
	}

	public void setMaximum(GeoPointEntity maximum) {
		this.maximum = maximum;
	}

	@Override
	public String toString() {
		return "BoundingBoxEntity [minimum=" + minimum + ", maximum=" + maximum + "]";
	}

}
