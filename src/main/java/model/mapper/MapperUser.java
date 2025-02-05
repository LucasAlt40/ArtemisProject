package model.mapper;


import model.dto.UserDetailsDto;
import model.dto.UserMinimalDto;
import model.entity.User;


public class MapperUser {

    public UserMinimalDto mapUserToUserMinimalDto (User user) {
       UserMinimalDto userMinimalDto = new UserMinimalDto();
       userMinimalDto.setName(user.getName());
       userMinimalDto.setUsername(user.getUsername());
       userMinimalDto.setPathProfilePicture(user.getPathProfilePicture());
        return userMinimalDto;
    }

    public UserDetailsDto mapUserToUserDetailsDto (User user) {
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setBiography(user.getBiography());
        userDetailsDto.setName(user.getName());
        userDetailsDto.setUsername(user.getUsername());
        userDetailsDto.setPathProfilePicture(user.getPathProfilePicture());
        userDetailsDto.setPathProfileBanner(user.getPathProfileBanner());
        userDetailsDto.setFriendsQuantity(user.getFriendsQuantity());
        return userDetailsDto;
    }
}
