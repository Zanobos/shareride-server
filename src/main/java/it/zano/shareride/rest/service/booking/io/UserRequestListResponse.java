package it.zano.shareride.rest.service.booking.io;

import java.util.Map;

import it.zano.shareride.rest.service.base.io.BaseResponse;
import it.zano.shareride.rest.service.booking.entities.UserRequest;

public class UserRequestListResponse extends BaseResponse {
	
	private Map<String,UserRequest> requestMap;

	public Map<String, UserRequest> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, UserRequest> requestMap) {
		this.requestMap = requestMap;
	}

	@Override
	public String toString() {
		return "UserRequestListResponse [requestMap=" + requestMap + "]";
	}
	

}
