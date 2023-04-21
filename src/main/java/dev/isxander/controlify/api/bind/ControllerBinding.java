package dev.isxander.controlify.api.bind;

import com.google.gson.JsonObject;
import dev.isxander.yacl.api.Option;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.function.BooleanSupplier;

public interface ControllerBinding {
    /**
     * @return the current analogue state of the binding.
     */
    float state();

    /**
     * @return the analogue state of the binding last tick.
     */
    float prevState();

    /**
     * @return if the binding is currently held.
     */
    boolean held();

    /**
     * @return if the binding was held last tick.
     */
    boolean prevHeld();

    /**
     * @return if the binding is held this tick but not the previous tick
     */
    boolean justPressed();

    /**
     * @return if the binding is not held this tick but was held last tick
     */
    boolean justReleased();

    Component name();
    Component description();
    Component category();

    ResourceLocation id();

    /**
     * The vanilla override of the binding. Null if there is no override.
     */
    @Nullable KeyMappingOverride override();

    void resetBind();
    boolean isUnbound();

    BindRenderer renderer();

    Option<?> generateYACLOption();

    JsonObject toJson();

    record KeyMappingOverride(KeyMapping keyMapping, BooleanSupplier toggleable) {
    }
}