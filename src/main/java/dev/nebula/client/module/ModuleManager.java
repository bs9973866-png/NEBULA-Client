package dev.nebula.client.module;

import java.util.*;
import java.util.stream.Collectors;

public final class ModuleManager {
    private static final List<Module> MODULES = new ArrayList<>();
    static {
        add(Module.Category.HUD, "HUD", "Show the NEBULA overlay", true);
        add(Module.Category.HUD, "Watermark", "Client branding", true);
        add(Module.Category.HUD, "FPS", "Frames per second", true);
        add(Module.Category.HUD, "Coordinates", "Player position", false);
        add(Module.Category.HUD, "Clock", "Local session clock", false);
        add(Module.Category.HUD, "Keystrokes", "Movement key display", false);
        add(Module.Category.HUD, "CPS", "Clicks per second display", false);
        add(Module.Category.HUD, "Ping", "Network latency display", false);
        add(Module.Category.HUD, "Speed", "Movement speed display", false);
        add(Module.Category.HUD, "Armor HUD", "Armor status panel", false);
        add(Module.Category.HUD, "Target HUD", "Target information panel", false);
        add(Module.Category.HUD, "Potion HUD", "Active status icons", false);
        add(Module.Category.HUD, "Direction", "Compass direction", false);
        add(Module.Category.HUD, "Session Info", "Session statistics", false);
        add(Module.Category.HUD, "Module List", "Enabled module list", false);
        add(Module.Category.HUD, "Boss Bar", "Custom boss bar layout", false);
        add(Module.Category.HUD, "Hotbar Style", "Custom hotbar presentation", false);
        add(Module.Category.HUD, "Chat Tweaks", "Chat spacing and readability", false);
        add(Module.Category.HUD, "Scoreboard", "Compact scoreboard layout", false);
        add(Module.Category.HUD, "Crosshair HUD", "Crosshair styling options", false);
        add(Module.Category.VISUAL, "Clean Screen", "Hide selected screen clutter", false);
        add(Module.Category.VISUAL, "Custom Font", "Use bundled NEBULA font in UI", true);
        add(Module.Category.VISUAL, "Dark UI", "Black and red interface theme", true);
        add(Module.Category.VISUAL, "Smooth UI", "Interpolate interface transitions", true);
        add(Module.Category.VISUAL, "Hit Marker", "Simple hit feedback indicator", false);
        add(Module.Category.VISUAL, "Hit Sounds", "Customizable hit feedback sound", false);
        add(Module.Category.VISUAL, "Swing Animation", "Alternative first-person swing style", false);
        add(Module.Category.VISUAL, "Low Fire", "Reduce fire overlay obstruction", false);
        add(Module.Category.VISUAL, "Clear Water", "Improve underwater visibility", false);
        add(Module.Category.VISUAL, "Time Changer", "Local visual time preference", false);
        add(Module.Category.VISUAL, "Weather Visuals", "Local weather presentation", false);
        add(Module.Category.VISUAL, "Item Glint", "Item shimmer presentation", false);
        add(Module.Category.VISUAL, "Particles", "Particle visibility preference", false);
        add(Module.Category.VISUAL, "Damage Tilt", "Camera tilt preference", false);
        add(Module.Category.VISUAL, "View Bobbing", "Camera motion preference", false);
        add(Module.Category.VISUAL, "Chat Timestamp", "Readable message timestamps", false);
        add(Module.Category.PVP, "Aim Trainer HUD", "Training overlay only", false);
        add(Module.Category.PVP, "Reach Display", "Display interaction distance", false);
        add(Module.Category.PVP, "Combo Counter", "Track consecutive hits", false);
        add(Module.Category.PVP, "Hit Stats", "Show local hit statistics", false);
        add(Module.Category.PVP, "Totem Counter", "Count visible totem use events", false);
        add(Module.Category.PVP, "Cooldown HUD", "Attack cooldown indicator", false);
        add(Module.Category.PVP, "Potion Timer", "Status effect time display", false);
        add(Module.Category.PVP, "Click Sounds", "UI click sound preference", false);
        add(Module.Category.PLAYER, "Auto Sprint", "Convenience sprint toggle", false);
        add(Module.Category.PLAYER, "Toggle Sneak", "Sneak key behavior preference", false);
        add(Module.Category.PLAYER, "Inventory Walk", "Movement while inventory is open", false);
        add(Module.Category.PLAYER, "Auto Tool", "Tool selection helper", false);
        add(Module.Category.PLAYER, "Item Switcher", "Quick hotbar selection helper", false);
        add(Module.Category.PLAYER, "Throw Preview", "Preview throw trajectory", false);
        add(Module.Category.PLAYER, "Free Look", "Camera look helper", false);
        add(Module.Category.PLAYER, "Fast Use", "Use-action convenience settings", false);
        add(Module.Category.PLAYER, "No Break Delay", "Local block-break pacing option", false);
        add(Module.Category.WORLD, "Block Overlay", "Block selection styling", false);
        add(Module.Category.WORLD, "World Info", "World and dimension info", false);
        add(Module.Category.WORLD, "Biome HUD", "Current biome readout", false);
        add(Module.Category.WORLD, "Light Level", "Light level information", false);
        add(Module.Category.WORLD, "Entity Count", "Nearby entity count", false);
        add(Module.Category.WORLD, "Structure Notes", "Personal waypoint notes", false);
        add(Module.Category.WORLD, "Waypoint HUD", "Personal waypoint markers", false);
        add(Module.Category.WORLD, "Chunk Border Key", "Shortcut preference for chunk borders", false);
        add(Module.Category.UTILITY, "Config Manager", "Save and load client settings", true);
        add(Module.Category.UTILITY, "GUI Scale", "Interface scaling preference", false);
        add(Module.Category.UTILITY, "Search", "Find modules quickly", true);
        add(Module.Category.UTILITY, "Keybinds", "Keyboard shortcut overview", false);
        add(Module.Category.UTILITY, "Notifications", "Compact status notifications", true);
        add(Module.Category.UTILITY, "Profile Switcher", "Named settings profiles", false);
        add(Module.Category.UTILITY, "Performance HUD", "Render and memory information", false);
        add(Module.Category.UTILITY, "Memory Monitor", "Heap usage readout", false);
        add(Module.Category.UTILITY, "FPS Limiter Hint", "Show frame cap status", false);
        add(Module.Category.UTILITY, "Resource Reload", "Quick resource reload shortcut", false);
        add(Module.Category.MISC, "Discord Presence", "Presence integration placeholder", false);
        add(Module.Category.MISC, "Client Brand", "NEBULA client label", true);
        add(Module.Category.MISC, "Stream Mode", "Hide selected personal UI fields", false);
        add(Module.Category.MISC, "Screenshot Mode", "Clean screenshot layout preference", false);
        add(Module.Category.MISC, "Language", "Client UI language preference", false);
        add(Module.Category.MISC, "Debug Panel", "Compact diagnostic information", false);
        add(Module.Category.MISC, "Welcome Toast", "Show client welcome notification", true);
    }
    private static void add(Module.Category category, String name, String description, boolean enabled) {
        MODULES.add(new Module(name, description, category, enabled));
    }
    public static List<Module> all() { return Collections.unmodifiableList(MODULES); }
    public static List<Module> byCategory(Module.Category category) {
        return MODULES.stream().filter(m -> m.category() == category).collect(Collectors.toList());
    }
    public static long count() { return MODULES.size(); }
}
