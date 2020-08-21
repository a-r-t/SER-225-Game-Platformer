package Builders;

import GameObject.Frame;
import Scene.Map;
import Scene.MapEntity;
import Scene.MapEntityStatus;

public class MapEntityBuilder extends GameObjectBuilder {
    protected MapEntityStatus mapEntityStatus = MapEntityStatus.ACTIVE;
    protected boolean isRespawnable = true;

    public MapEntityBuilder() { }

    public MapEntityBuilder(Frame frame) {
        super(frame);
    }

    public MapEntityBuilder(Frame[] frames) {
        super(frames);
    }

    public MapEntityBuilder withMapEntityStatus(MapEntityStatus mapEntityStatus) {
        this.mapEntityStatus = mapEntityStatus;
        return this;
    }

    public MapEntityBuilder isRespawnable(boolean isRespawnable) {
        this.isRespawnable = isRespawnable;
        return this;
    }

    public MapEntity build(float x, float y, Map map) {
        MapEntity mapEntity = new MapEntity(x, y, cloneAnimations(), startingAnimationName);
        mapEntity.setMapEntityStatus(mapEntityStatus);
        mapEntity.setIsRespawnable(isRespawnable);
        return mapEntity;
    }
}