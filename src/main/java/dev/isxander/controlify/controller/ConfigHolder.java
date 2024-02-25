package dev.isxander.controlify.controller;

public interface ConfigHolder<T extends ConfigClass> {
    IConfig<T> config();

    default T confObj() {
        return this.config().config();
    }

    default T defObj() {
        return this.config().defaultConfig();
    }
}
