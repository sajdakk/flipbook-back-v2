package com.sajdakk.flipbook.dtos;

import lombok.Data;

@Data
public class AddAvatarDto {
    private byte[] bytes;
    private String imageExtension;

    public AddAvatarDto(byte[] bytes, String imageExtension) {
        this.bytes = bytes;
        this.imageExtension = imageExtension;
    }
}
