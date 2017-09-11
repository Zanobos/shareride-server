package it.zano.shareride.rest.service.booking.entities;

public class UserRequest {
	
	private String requestId;
	private Location askedPickup;
	private Location proposedPickup;
	private Location askedDevilery;
	private Location proposedDevilery;
	//TODO complete route and/or path between pickup and delivery
	
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public Location getAskedPickup() {
		return askedPickup;
	}
	public void setAskedPickup(Location askedPickup) {
		this.askedPickup = askedPickup;
	}
	public Location getProposedPickup() {
		return proposedPickup;
	}
	public void setProposedPickup(Location proposedPickup) {
		this.proposedPickup = proposedPickup;
	}
	public Location getAskedDevilery() {
		return askedDevilery;
	}
	public void setAskedDevilery(Location askedDevilery) {
		this.askedDevilery = askedDevilery;
	}
	public Location getProposedDevilery() {
		return proposedDevilery;
	}
	public void setProposedDevilery(Location proposedDevilery) {
		this.proposedDevilery = proposedDevilery;
	}
	@Override
	public String toString() {
		return "UserRequest [requestId=" + requestId + ", askedPickup=" + askedPickup + ", proposedPickup="
				+ proposedPickup + ", askedDevilery=" + askedDevilery + ", proposedDevilery=" + proposedDevilery + "]";
	}


}
