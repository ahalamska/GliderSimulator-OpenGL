package Engine.models;

import Engine.Textures.TextureModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelWithTexture {

    private RawModel rawModel;
    private TextureModel texture;


}
