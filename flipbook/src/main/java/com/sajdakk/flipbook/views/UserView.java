package com.sajdakk.flipbook.views;

import com.sajdakk.flipbook.entities.FavoriteEntity;
import com.sajdakk.flipbook.entities.ReviewEntity;
import com.sajdakk.flipbook.entities.RoleEntity;
import com.sajdakk.flipbook.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class UserView {
    private Integer id;
    private String email;
    private String name;
    private String surname;
    private String avatar;
    private RoleEntity role;

    public static List<UserView> fromEntities(List<UserEntity> entities) {
        return entities.stream().map(UserView::fromEntity).toList();
    }

    public static UserView fromEntity(UserEntity entity) {
        return UserView.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .surname(entity.getSurname())
                .avatar(entity.getAvatar())
                .role(entity.getRole())
                .build();
    }
}
