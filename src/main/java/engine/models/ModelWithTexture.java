package engine.models;

import engine.Textures.TextureModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelWithTexture {

    private RawModel rawModel;
    private TextureModel texture;


}
