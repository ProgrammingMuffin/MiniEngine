package engine.strategy;

import engine.models.IRenderData;

public interface IRenderingStrategy {

    void execute(IRenderData renderData);
}