package net.spaceeye.vmod.translate

import net.minecraft.client.resources.language.I18n
import net.minecraft.network.chat.Component

private inline fun makeComponent(s: String) = Component.translatable(s)
inline fun Component.get(): String = I18n.get(this.string)

private const val path = "vmod.gui."
private inline fun t(s: String) = makeComponent(path+s)
private inline fun s(s: String) = makeComponent(path+"setting."+s)
private inline fun x(s: String) = makeComponent(path+"text."+s)

val CONNECTION = t("connection")
val ROPE = t("rope")
val HYDRAULICS = t("hydraulics")
val PHYS_ROPE = t("phys_rope")
val WINCH = t("winch")

val GRAVITY_CHANGER = t("gravity_changer")
val DISABLE_COLLISIONS = s("disable_collisions")
val SCHEMATIC = t("schematic")
val SCALE = t("scale")
val STRIP = t("strip")

val COMPLIANCE = s("compliance")
val MAX_FORCE = s("max_force")
val FIXED_DISTANCE = s("fixed_distance")
val WIDTH = s("width")
val SEGMENTS = s("segments")
val EXTENSION_SPEED = s("extension_speed")
val EXTENSION_DISTANCE = s("extension_distance")
val CHANNEL = s("channel")
val FUNCTION = s("function")
val ACTIVATE = s("activate")
val DEACTIVATE = s("deactivate")
val APPLY_CHANGES = s("apply_changes")
val REMOVED = s("removed")
val DISTANCE_FROM_BLOCK = s("distance_from_block")
val RADIUS = s("radius")
val MASS_PER_SEGMENT = s("mass_per_segment")

val PLACEMENT_ASSIST_SCROLL_STEP = s("placement_assist_scroll_step")

val HITPOS_MODES = s("hitpos_modes")
val NORMAL = s("normal")
val CENTERED_ON_SIDE = s("centered_on_side")
val CENTERED_IN_BLOCK = s("centered_in_block")

val HYDRAULICS_INPUT_MODES = s("input_modes")
val TOGGLE = s("toggle")
val SIGNAL = s("signal")

val MESSAGER_MODE = s("messager_mode")

val STRIP_MODES = s("strip_modes")
val STRIP_ALL = s("strip_all")
val STRIP_IN_RADIUS = s("strip_in_radius")

val CONNECTION_MODES = s("connection_modes")
val FIXED_ORIENTATION = s("fixed_orientation")
val HINGE_ORIENTATION = s("hinge_orientation")
val FREE_ORIENTATION = s("free_orientation")

val SAVE = s("save")
val CANCEL = s("cancel")
val FILENAME = s("filename")
val LOAD = s("load")

val X_GRAVITY = s("x_gravity")
val Y_GRAVITY = s("y_gravity")
val Z_GRAVITY = s("z_gravity")

val SAMPLING_MODES = s("sampling_modes")

val INDIVIDUAL = s("individual")
val ALL_CONNECTED = s("all_connected")
val ALL_CONNECTED_AND_TOUCHING = s("all_connected_and_touching")

val COMMON_HUD_1 = x("common_hud_1")
val COMMON_HUD_2 = x("common_hud_2")
val COMMON_HUD_3 = x("common_hud_3")
val COMMON_HUD_4 = x("common_hud_4")
val COMMON_HUD_5 = x("common_hud_5")

val DISABLE_COLLISIONS_HUD_1 = x("disable_collisions_hud_1")
val SCALE_HUD_1 = x("scale_hud_1")

val SCHEM_HUD_1 = x("schem_hud_1")
val SCHEM_HUD_2 = x("schem_hud_2")

val STRIP_HUD_1 = x("strip_hud_1")

val GRAV_CHANGER_HUD_1 = x("grav_changer_hud_1")