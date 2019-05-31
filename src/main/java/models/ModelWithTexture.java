package models;

import Textures.TextureModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ModelWithTexture {

    private RawModel rawModel;
    private TextureModel texture;


}
