package com.example.volcanoevacuation.game;

public final class BarricadeService {

    public boolean breakBarricade(Map map, Position pos, Counter breaksLeft) {
        return transformTile(map, pos, Tile.BARRICADE, Tile.ROAD, breaksLeft);
    }

    public boolean placeBarricade(Map map, Position pos, Counter placesLeft) {
        return transformTile(map, pos, Tile.ROAD, Tile.BARRICADE, placesLeft);
    }

    private boolean transformTile(Map map, Position pos, Tile required, Tile result, Counter counter) {
        if (counter.get() <= 0) return false;
        if (map.getTile(pos) != required) return false;

        map.setTile(pos, result);
        counter.decrement();
        return true;
    }

    public static final class Counter {
        private int value;

        public Counter(int initialValue) {
            this.value = initialValue;
        }

        public int get() {
            return value;
        }

        public void decrement() {
            value--;
        }
    }
}

