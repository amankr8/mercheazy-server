package com.mercheazy.server.service.impl;

import com.mercheazy.server.dto.user.AddressRequestDto;
import com.mercheazy.server.dto.user.PhoneRequestDto;
import com.mercheazy.server.dto.user.ProfileRequestDto;
import com.mercheazy.server.entity.Country;
import com.mercheazy.server.entity.user.Address;
import com.mercheazy.server.entity.user.AuthUser;
import com.mercheazy.server.entity.user.Phone;
import com.mercheazy.server.entity.user.Profile;
import com.mercheazy.server.exception.ResourceNotFoundException;
import com.mercheazy.server.repository.CountryRepository;
import com.mercheazy.server.repository.user.ProfileRepository;
import com.mercheazy.server.repository.user.UserRepository;
import com.mercheazy.server.service.CartService;
import com.mercheazy.server.service.UserService;
import com.mercheazy.server.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CountryRepository countryRepository;
    private final CartService cartService;

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
    public AuthUser addUserProfile(AuthUser authUser, Profile profile) {
        AuthUser newUser = userRepository.save(authUser);

        // Create user cart
        cartService.createUserCart(newUser);

        // Create default profile
        profile.setAuthUser(newUser);
        newUser.setProfiles(Collections.singletonList(profile));

        return userRepository.save(newUser);
    }

    @Override
    public Profile addProfile(ProfileRequestDto profileRequestDto) {
        AuthUser loggedInUser = AuthUtil.getLoggedInUser();
        Profile profile = Profile.builder()
                .authUser(loggedInUser)
                .name(profileRequestDto.getName())
                .build();
        profile = profileRepository.save(profile);

        if (profileRequestDto.getAddressRequestDto() != null && profileRequestDto.getPhoneRequestDto() != null) {
            return profile;
        } else if (profileRequestDto.getAddressRequestDto() != null) {
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
        } else {
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
