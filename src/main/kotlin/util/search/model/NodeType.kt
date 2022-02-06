package util.search.model

enum class NodeType {
    ROOM_CORNER_TILE, // Inaccessible
    MATRIX_SIDE_TILE, // Inaccessible
    ROOM_TILE, // No workaround needed
    CORRIDOR_TILE, // Need some verification
    EMPTY_TILE //
}