package util.graph

enum class NodeType {
    ROOM_CORNER_TILE, // Inaccessible
    MATRIX_SIDE_TILE, // Inaccessible
    ROOM_TILE, // No workaround needed
    ROOM_ENTRANCE_TILE, // Need some verification
    CORRIDOR_TILE, // Need some verification
    EMPTY_TILE //


}