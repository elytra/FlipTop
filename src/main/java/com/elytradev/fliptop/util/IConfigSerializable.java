package com.elytradev.fliptop.util;

public interface IConfigSerializable {
    public String toConfigString();
    public boolean matches(String configName);
}