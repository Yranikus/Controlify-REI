package dev.isxander.controlify.controller;

import dev.isxander.controlify.controller.serialization.ConfigClass;
import dev.isxander.controlify.utils.CUtil;
import net.minecraft.resources.ResourceLocation;

public class JoystickControllerConfig implements ConfigClass {
    public static final ResourceLocation ID = CUtil.rl("config/joystick");
}
