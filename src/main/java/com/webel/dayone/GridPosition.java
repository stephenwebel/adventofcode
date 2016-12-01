package com.webel.dayone;

import lombok.Builder;
import lombok.Value;

/**
 * Created by swebel on 12/1/2016.
 */
@Value
@Builder(builderClassName = "Builder")
public class GridPosition {
    int x;
    int y;
}
