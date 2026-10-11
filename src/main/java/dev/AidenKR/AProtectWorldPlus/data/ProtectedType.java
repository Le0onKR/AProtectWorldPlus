package dev.AidenKR.AProtectWorldPlus.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProtectedType {

    PVP("pvp"),
    DROPITEM("drop_item"),
    EXHAUST("exhaust"),
    FALLDAMAGE("falling_damage"),
    INVENSAVE("invensave"),
    BREAK("block_break"),
    INTER("click_inter"),
    PLACE("block_place");

    private final String name;
}
