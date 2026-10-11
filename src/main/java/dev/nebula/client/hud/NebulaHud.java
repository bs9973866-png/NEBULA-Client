package dev.nebula.client.hud;

import dev.nebula.client.NebulaClient;
import dev.nebula.client.module.ModuleManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.math.MathHelper;

public final class NebulaHud {
    private NebulaHud() {}
    private static Text ui(String s) { return Text.literal(s).setStyle(Style.EMPTY.withFont(NebulaClient.FONT)); }
    public static void render(DrawContext ctx) {
        MinecraftClient mc=MinecraftClient.getInstance();
        if(mc.options.hudHidden || mc.player==null || mc.currentScreen instanceof dev.nebula.client.gui.NebulaClickGui) return;
        if(!ModuleManager.all().stream().anyMatch(m->m.name().equals("HUD")&&m.enabled())) return;
        int x=10,y=10;
        ctx.fill(x,y,x+118,y+27,0xC90B0B0F);ctx.fill(x,y,x+2,y+27,0xFFFF3548);
        ctx.drawText(mc.textRenderer,ui("NEBULA"),x+9,y+5,0xFFFF3548,false);
        ctx.drawText(mc.textRenderer,ui("CLIENT"),x+63,y+5,0xFFF1F1F4,false);
        boolean fps=ModuleManager.all().stream().anyMatch(m->m.name().equals("FPS")&&m.enabled());
        if(fps){int fy=y+33;ctx.fill(x,fy,x+90,fy+22,0xC90B0B0F);ctx.drawText(mc.textRenderer,ui("FPS  "+mc.getCurrentFps()),x+8,fy+7,0xFFF1F1F4,false);}
        boolean coords=ModuleManager.all().stream().anyMatch(m->m.name().equals("Coordinates")&&m.enabled());
        if(coords){String pos=String.format(java.util.Locale.ROOT,"XYZ  %d  %d  %d",MathHelper.floor(mc.player.getX()),MathHelper.floor(mc.player.getY()),MathHelper.floor(mc.player.getZ()));int py=heightSafe(mc)-31;ctx.fill(x,py,x+190,py+21,0xC90B0B0F);ctx.drawText(mc.textRenderer,ui(pos),x+8,py+6,0xFFF1F1F4,false);}
    }
    private static int heightSafe(MinecraftClient mc){return mc.getWindow().getScaledHeight();}
}
