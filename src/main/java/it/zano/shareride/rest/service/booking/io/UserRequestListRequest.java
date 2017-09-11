package it.zano.shareride.rest.service.booking.io;

import org.joda.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.joda.deser.LocalDateDeserializer;

import it.zano.shareride.rest.service.base.io.BaseRequest;
import it.zano.shareride.utils.EnumStatus;

public class UserRequestListRequest extends BaseRequest {
	
	private String userId;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date;
	private EnumStatus status;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EnumStatus getStatus() {
		return status;
	}

	public void setStatus(EnumStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserRequestListRequest [userId=" + userId + ", date=" + date + ", status=" + status + "]";
	}
	
	
}
