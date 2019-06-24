package Engine.Textures;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Getter
public class TextureData {

    private final ByteBuffer buffer;
    private final int width;
    private final int height;
}
