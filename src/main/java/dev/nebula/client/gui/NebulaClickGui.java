package dev.nebula.client.gui;

import dev.nebula.client.NebulaClient;
import dev.nebula.client.module.Module;
import dev.nebula.client.module.ModuleManager;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import java.util.*;

public final class NebulaClickGui extends Screen {
    private static final int BG = 0xF20C0C10, PANEL = 0xFF141419, PANEL2 = 0xFF1C1C23, RED = 0xFFFF3548, MUTED = 0xFF8A8A98, WHITE = 0xFFF4F4F7;
    private final boolean editMode;
    private Module.Category selected = Module.Category.HUD;
    private TextFieldWidget search;
    private int scroll;
    private boolean dragging;
    private double dragX, dragY;
    private int panelX = 0, panelY = 0;
    public NebulaClickGui() { this(false); }
    public NebulaClickGui(boolean editMode) { super(Text.literal("NEBULA")); this.editMode = editMode; }
    private Text styled(String s) { return Text.literal(s).setStyle(Style.EMPTY.withFont(NebulaClient.FONT)); }
    @Override protected void init() {
        panelX = Math.max(12, (width - 560) / 2); panelY = Math.max(12, (height - 350) / 2);
        search = new TextFieldWidget(textRenderer, panelX + 190, panelY + 42, 210, 22, Text.literal("Search modules"));
        search.setPlaceholder(styled("Search modules...")); search.setMaxLength(48); addDrawableChild(search);
    }
    @Override public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, width, height, 0x99000000);
        int x=panelX, y=panelY, w=560, h=350;
        ctx.fill(x, y, x+w, y+h, BG); ctx.fill(x, y, x+3, y+h, RED);
        ctx.fill(x+12,y+12,x+548,y+38,PANEL);
        ctx.drawText(textRenderer, styled("NEBULA"), x+22,y+19, RED, false);
        ctx.drawText(textRenderer, styled(editMode ? "HUD EDITOR" : "CLIENT  /  MODULES"), x+104,y+19, MUTED, false);
        ctx.drawText(textRenderer, styled("1.21.1  •  FABRIC"), x+w-126,y+19, MUTED, false);
        ctx.fill(x+12,y+44,x+160,y+h-14,PANEL);
        int cy=y+54;
        for (Module.Category c: Module.Category.values()) {
            boolean active = c==selected;
            if(active) { ctx.fill(x+18,cy-4,x+154,cy+20,0xFF2B171C); ctx.fill(x+18,cy-4,x+21,cy+20,RED); }
            ctx.drawText(textRenderer, styled(c.name()), x+29,cy+3, active?WHITE:MUTED,false);
            cy+=31;
        }
        ctx.drawText(textRenderer, styled("MODULES  /  "+ModuleManager.count()), x+176,y+78,WHITE,false);
        ctx.drawText(textRenderer, styled(editMode ? "Drag HUD panels in-game after closing this screen" : "Click a card to toggle  •  Right Shift to close"), x+176,y+94,MUTED,false);
        int listY=y+116, rowH=48, listBottom=y+h-14;
        ctx.enableScissor(x+172,listY,x+w-12,listBottom);
        List<Module> modules=ModuleManager.byCategory(selected); String q=search==null?"":search.getText().toLowerCase(Locale.ROOT);
        int idx=0, shown=0;
        for(Module m:modules) {
            if(!q.isBlank()&&!m.name().toLowerCase(Locale.ROOT).contains(q)&&!m.description().toLowerCase(Locale.ROOT).contains(q)) continue;
            int ry=listY+shown* (rowH+7)-scroll; shown++;
            if(ry+rowH<listY||ry>listBottom) continue;
            ctx.fill(x+174,ry,x+w-14,ry+rowH,m.enabled()?0xFF25171B:PANEL);
            ctx.fill(x+174,ry,x+176,ry+rowH,m.enabled()?RED:0xFF292930);
            ctx.drawText(textRenderer,styled(m.name()),x+187,ry+9,WHITE,false);
            ctx.drawText(textRenderer,styled(m.description()),x+187,ry+26,MUTED,false);
            int sx=x+w-52, sy=ry+16; ctx.fill(sx,sy,sx+25,sy+13,m.enabled()?RED:0xFF3A3A43);
            ctx.fill(m.enabled()?sx+14:sx+2,sy+2,m.enabled()?sx+23:sx+11,sy+11,WHITE);
            idx++;
        }
        ctx.disableScissor();
        ctx.drawText(textRenderer,styled("NEBULA  •  BLACK / RED"),x+18,y+h-26,0xFF6F6F7A,false);
        super.render(ctx,mouseX,mouseY,delta);
    }
    @Override public boolean mouseClicked(double mx,double my,int button) {
        if(button==0) {
            int x=panelX,y=panelY;
            if(my>=y+12&&my<=y+38&&mx>=x+12&&mx<=x+548) { dragging=true;dragX=mx-x;dragY=my-y; return true; }
            int cy=y+50;
            for(Module.Category c:Module.Category.values()) { if(mx>=x+15&&mx<=x+160&&my>=cy&&my<=cy+25){selected=c;scroll=0;return true;} cy+=31; }
            if(my>=y+116&&my<y+336&&mx>=x+172&&mx<=x+546) {
                List<Module> list=ModuleManager.byCategory(selected); String q=search.getText().toLowerCase(Locale.ROOT); int row=(int)((my-(y+116)+scroll)/55); int n=0;
                for(Module m:list) { if(!q.isBlank()&&!m.name().toLowerCase(Locale.ROOT).contains(q)&&!m.description().toLowerCase(Locale.ROOT).contains(q))continue; if(n++==row){m.toggle();return true;} }
            }
        }
        return super.mouseClicked(mx,my,button);
    }
    @Override public boolean mouseDragged(double mx,double my,int button,double dx,double dy) { if(dragging){panelX=MathHelper.clamp((int)(mx-dragX),0,Math.max(0,width-560));panelY=MathHelper.clamp((int)(my-dragY),0,Math.max(0,height-350));return true;} return super.mouseDragged(mx,my,button,dx,dy); }
    @Override public boolean mouseReleased(double mx,double my,int button){dragging=false;return super.mouseReleased(mx,my,button);}
    @Override public boolean mouseScrolled(double mx,double my,double horizontal,double vertical){scroll=Math.max(0,scroll-(int)(vertical*44));return true;}
    @Override public boolean keyPressed(int key,int scanCode,int modifiers){if(search!=null&&search.isFocused()&&search.keyPressed(key,scanCode,modifiers))return true;return super.keyPressed(key,scanCode,modifiers);}
    @Override public boolean charTyped(char chr,int modifiers){if(search!=null&&search.charTyped(chr,modifiers))return true;return super.charTyped(chr,modifiers);}
    @Override public boolean shouldPause(){return false;}
}
