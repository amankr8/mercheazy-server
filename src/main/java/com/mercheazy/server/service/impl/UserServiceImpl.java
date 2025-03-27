package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.user.AddressRequestDto;
import com.mercheazy.server.dto.user.PhoneRequestDto;
import com.mercheazy.server.dto.user.ProfileRequestDto;
import com.mercheazy.server.entity.Country;
import com.mercheazy.server.entity.user.*;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CountryRepository;
import com.mercheazy.server.repository.user.ProfileRepository;
import com.mercheazy.server.repository.user.UserRepository;
import com.mercheazy.server.repository.user.UserTokenRepository;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CountryRepository countryRepository;
    private final UserTokenRepository userTokenRepository;

    @Override
    public List<AuthUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public AuthUser getUserById(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    @Override
    public List<Profile> getProfilesByUserId(int userId) {
        AuthUser user = getUserById(userId);
        return user.getProfiles();
    }

    @Override
    public void saveUserToken(AuthUser authUser, String token) {
        UserToken userToken = UserToken.builder()
                .authUser(authUser)
                .token(token)
                .build();
        userTokenRepository.save(userToken);
    }

    @Override
    public Profile addProfile(ProfileRequestDto profileRequestDto) {
        AuthUser loggedInUser = AuthUtil.getLoggedInUser();
        Profile profile = Profile.builder()
                .authUser(loggedInUser)
                .name(profileRequestDto.getName())
                .build();

        if (profileRequestDto.getAddressRequestDto() != null) {
            AddressRequestDto addressRequestDto = profileRequestDto.getAddressRequestDto();
            Country country = countryRepository.findByName(addressRequestDto.getCountry())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found!"));
            Address address = Address.builder()
                    .profile(profile)
                    .house(addressRequestDto.getHouse())
                    .street(addressRequestDto.getStreet())
                    .city(addressRequestDto.getCity())
                    .state(addressRequestDto.getState())
                    .country(country)
                    .zip(addressRequestDto.getZip())
                    .build();
            profile.setAddress(address);
        }

        if (profileRequestDto.getPhoneRequestDto() != null) {
            PhoneRequestDto phoneRequestDto = profileRequestDto.getPhoneRequestDto();
            Country countryPhone = countryRepository.findByName(phoneRequestDto.getCountryCode())
                    .orElseThrow(() -> new ResourceNotFoundException("Country not found!"));
            Phone phone = Phone.builder()
                    .country(countryPhone)
                    .profile(profile)
                    .build();
            profile.setPhone(phone);
        }

        return profileRepository.save(profile);
    }
}
