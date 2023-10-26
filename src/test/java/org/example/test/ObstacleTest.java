package org.example.test;


import org.example.client.Obstacle;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.List;
import java.awt.Color;
public class ObstacleTest {
    @Test
    public void testObstacles() {
        assertEquals(40, Obstacle.size);

        // Assert the color constant
        assertEquals(Color.decode("#097950"), Obstacle.color);

        // Assert the positions array
        int[][] expectedPositions = {
                {-690, -690}, {-510, -690}, {-330, -690}, {-150, -690}, {210, -690}, {390, -690}, {570, -690}, {750, -690},
                {-690, -510}, {-510, -510}, {-330, -510}, {-150, -510}, {210, -510}, {390, -510}, {570, -510}, {750, -510},
                {-690, -330}, {-510, -330}, {-330, -330}, {-150, -330}, {30, -330}, {210, -330}, {390, -330}, {570, -330}, {750, -330},
                {-690, -150}, {-510, -150}, {-330, -150}, {-150, -150}, {30, -150}, {210, -150}, {390, -150}, {570, -150}, {750, -150},
                {-690, 30}, {-510, 30}, {-330, 30}, {-150, 30}, {210, 30}, {390, 30}, {570, 30}, {750, 30},
                {-690, 210}, {-510, 210}, {-330, 210}, {-150, 210}, {30, 210}, {210, 210}, {390, 210}, {570, 210}, {750, 210},
                {-690, 390}, {-510, 390}, {-330, 390}, {-150, 390}, {30, 390}, {210, 390}, {390, 390}, {570, 390}, {750, 390},
                {-690, 570}, {-510, 570}, {-330, 570}, {-150, 570}, {30, 570}, {210, 570}, {390, 570}, {570, 570}, {750, 570},
                {-690, 750}, {-510, 750}, {-330, 750}, {-150, 750}, {30, 750}, {210, 750}, {390, 750}, {570, 750}, {750, 750}
        };
        assertArrayEquals(expectedPositions, Obstacle.positions);
    }
}
