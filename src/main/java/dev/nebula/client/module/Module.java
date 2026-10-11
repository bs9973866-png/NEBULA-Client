package dev.nebula.client.module;

public final class Module {
    public enum Category { HUD, VISUAL, PVP, PLAYER, WORLD, UTILITY, MISC }
    private final String name, description;
    private final Category category;
    private boolean enabled;
    public Module(String name, String description, Category category, boolean enabled) {
        this.name = name; this.description = description; this.category = category; this.enabled = enabled;
    }
    public String name() { return name; }
    public String description() { return description; }
    public Category category() { return category; }
    public boolean enabled() { return enabled; }
    public void toggle() { enabled = !enabled; }
    public void setEnabled(boolean value) { enabled = value; }
}
