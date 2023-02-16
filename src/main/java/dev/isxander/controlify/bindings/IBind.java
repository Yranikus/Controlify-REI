package dev.isxander.controlify.bindings;

import com.google.gson.JsonObject;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.isxander.controlify.Controlify;
import dev.isxander.controlify.controller.*;
import dev.isxander.controlify.controller.gamepad.GamepadController;
import dev.isxander.controlify.controller.joystick.JoystickController;
import dev.isxander.controlify.gui.DrawSize;

public interface IBind<S extends ControllerState> {
    float state(S state);
    default boolean held(S state, Controller<S, ?> controller) {
        return state(state) > controller.config().buttonActivationThreshold;
    }

    void draw(PoseStack matrices, int x, int centerY, Controller<S, ?> controller);
    DrawSize drawSize();

    JsonObject toJson();

    @SuppressWarnings("unchecked")
    static <T extends ControllerState> IBind<T> fromJson(JsonObject json, Controller<T, ?> controller) {
        var type = json.get("type").getAsString();
        if (type.equals(EmptyBind.BIND_ID))
            return new EmptyBind<>();

        if (controller instanceof GamepadController && type.equals(GamepadBind.BIND_ID)) {
            return (IBind<T>) GamepadBind.fromJson(json);
        } else if (controller instanceof JoystickController joystick) {
            return (IBind<T>) switch (type) {
                case JoystickButtonBind.BIND_ID -> JoystickButtonBind.fromJson(json, joystick);
                case JoystickHatBind.BIND_ID -> JoystickHatBind.fromJson(json, joystick);
                case JoystickAxisBind.BIND_ID -> JoystickAxisBind.fromJson(json, joystick);
                default -> {
                    Controlify.LOGGER.error("Unknown bind type: " + type);
                    yield new EmptyBind<>();
                }
            };
        }

        Controlify.LOGGER.error("Could not parse bind for controller: " + controller.name());
        return new EmptyBind<>();
    }
}
