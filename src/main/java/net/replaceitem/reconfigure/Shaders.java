package net.replaceitem.reconfigure;

import net.minecraft.client.gl.Defines;
import net.minecraft.client.gl.ShaderProgramKey;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.VertexFormats;

public class Shaders {
    public static final ShaderProgramKey COLORPICKER = new ShaderProgramKey(Reconfigure.id("core/colorpicker"), VertexFormats.POSITION_COLOR, Defines.EMPTY);
    
    
    public static void init() {
        ShaderProgramKeys.getAll().add(COLORPICKER);
    }
}
