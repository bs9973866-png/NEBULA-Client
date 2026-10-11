package dev.nebula.client;

import dev.nebula.client.gui.NebulaClickGui;
import dev.nebula.client.hud.NebulaHud;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class NebulaClient implements ClientModInitializer {
    public static final String MOD_ID = "nebula";
    public static final Identifier FONT = Identifier.of(MOD_ID, "nebula");
    private static KeyBinding clickGuiKey;
    private static KeyBinding hudEditKey;
    @Override public void onInitializeClient() {
        clickGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.nebula.clickgui", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.nebula" ));
        hudEditKey = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.nebula.hud_editor", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL, "category.nebula" ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (clickGuiKey.wasPressed()) if (client.currentScreen == null) client.setScreen(new NebulaClickGui());
            while (hudEditKey.wasPressed()) if (client.currentScreen == null) client.setScreen(new NebulaClickGui(true));
        });
        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> NebulaHud.render(drawContext));
    }
}
