package model.dto;

import model.entity.RequestStatus;
import model.entity.User;

public record RequestDto(User userSubmitted, RequestStatus status) {

}
